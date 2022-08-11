package ru.surf.intern_source_code;

//следует избегать общих импортов .*
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

//Код не работает в принципе
public class Index {
    /*
    Кажется, что поля могут быть final, так же желательно полностью скрыть их, поставить модификатор private,
    чтобы вся логика операций над этими данными была инкапсулирована в класс.
    Так же было бы логично использовать HashMap как значение, в случае, если документов будет много.
     */
    TreeMap<String, List<Pointer>> invertedIndex;
    ExecutorService pool;

    /*
    Не желательно чтобы класс отвечал за создание собственных полей, это противоречит DI,
    что если мы захотим передать какую-то другую реализацию? а если захотим сделать какие-то пресеты данных для индекса?
     */
    public Index(ExecutorService pool) {
        this.pool = pool;
        invertedIndex = new TreeMap<>();
    }

    /*
    Желательно вынести логику чтения данных в другой класс, а сюда подавать уже данные в памяти/преобразованные в обратный
    индекс данные, чтобы наш класс оставался независим от источника данных.
    Данный метод очень просто может оставить класс не консистентным, т.к запускает асинхронные задачи и
    не ждет их завершения/не синхронизирует invertedIndex.
     */
    public void indexAllTxtInPath(String pathToDir) throws IOException {
        Path of = Path.of(pathToDir);

        //Откуда взялось число 2? что если в индексируемой директории больше 2х файлов?
        BlockingQueue<Path> files = new ArrayBlockingQueue<>(2);

        //Судя по названию метода, он должен проиндексировать ВСЕ txt в директории, но Files.list - не рекурсивный
        try (Stream<Path> stream = Files.list(of)) {
            stream.forEach(files::add);
        }

        /*
         1)Сайд эффектов желательно избежать, менять invertedIndex таким образом - bad practice, тем более,
         что он никак не синхронизован. В данном случае использовать ForkJoinPool, сделать наши таски Callablе, заджойниться
         на них, подождать исполнения и сложить результаты в invertedIndex. Или же использовать уже готовую реализацию
         ForkJoinPool и RecursiveTask. Второй вариант предпочтительнее.
         2)Тут мы гвоздями прибили индексирование всего 3х файлов.
         */
        pool.submit(new IndexTask(files));
        pool.submit(new IndexTask(files));
        pool.submit(new IndexTask(files));
    }

    /*
    Безопасно ли отдавать клиенту индекс, не хотелось бы что бы он менялся без ведома класса?
    Я бы сделал этот метод как минимум package private.
    */
    public TreeMap<String, List<Pointer>> getInvertedIndex() {
        return invertedIndex;
    }

    //По java code style не принято называть методы с большой буквы.
    public List<Pointer> GetRelevantDocuments(String term) {
        return invertedIndex.get(term);
    }

    public Optional<Pointer> getMostRelevantDocument(String term) {
        return invertedIndex.get(term).stream().max(Comparator.comparing(o -> o.count));
    }

    /*
    У нас есть public методы у InvertedIndex, возвращающие экземпляр данного класса, но получить вне пакета мы его не сможем.
    Нужно либо сделать эти методы package private, либо открыть класс Pointer всем.
     */
    static class Pointer {
        //достать эти поля мы можем только из текущего root класса, count вполне может не влезть в Integer
        private Integer count;
        //поле может быть final
        private String filePath;

        public Pointer(Integer count, String filePath) {
            this.count = count;
            this.filePath = filePath;
        }

        @Override
        public String toString() {
            return "{" + "count=" + count + ", filePath='" + filePath + '\'' + '}';
        }
    }

    /*
    Мне кажется было бы разумно вынести таску
    в пакет с различными реализациями индекса, для переиспользования.
    Эта таска, а не команда, я бы сделал ее Callable/RecursiveTask.
     */
    class IndexTask implements Runnable {

        private final BlockingQueue<Path> queue;

        public IndexTask(BlockingQueue<Path> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                //Потенциально заблокирует нам поток без проверки на isEmpty
                Path take = queue.take();
                //Тут потенциальная долгая блокировка одного потока
                List<String> strings = Files.readAllLines(take);

                /*
                Обычно в java принято форматировать код с переносом точки на новую строку, по типу:
                strings.stream()
                .flatmap()
                .forEach()...
                 */
                strings.stream().flatMap(str -> Stream.of(str.split(" "))).forEach(word -> invertedIndex.compute(word, (k, v) -> {
                    /*
                    Я бы не стал использовать if без {}, это сильно уменьшает читабельность, к тому же, я бы вынес эту лямбду
                    куда-нибудь, например в приватный метод или стратегию.
                     */
                    if (v == null) return List.of(new Pointer(1, take.toString()));
                    else {
                        ArrayList<Pointer> pointers = new ArrayList<>();

                        if (v.stream().noneMatch(pointer -> pointer.filePath.equals(take.toString()))) {
                            pointers.add(new Pointer(1, take.toString()));
                        }

                        v.forEach(pointer -> {
                            if (pointer.filePath.equals(take.toString())) {
                                pointer.count = pointer.count + 1;
                            }
                        });

                        pointers.addAll(v);

                        return pointers;
                    }

                }));

            } catch (InterruptedException | IOException e) {
                /*
                Если нам вылетела ошибка, желательно погасить пул потоков, в данном случае мы не делаем этого сразу и отдаем
                на сторону клиента, при том что перебрасываемый ex - вообще ни о чем не говорит. Нужно либо уйти от использования
                RuntimeException, либо гасить пул прямо в здесь.
                 */
                throw new RuntimeException();
            }
        }
    }
}
