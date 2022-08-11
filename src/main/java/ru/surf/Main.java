package ru.surf;

import ru.surf.solution.factories.DefaultDiskIndexerFactory;
import ru.surf.solution.factories.signature.DiskIndexerFactory;
import ru.surf.solution.index.*;
import ru.surf.solution.indexer.AbstractBlockingDiskIndexer;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //создание фабрики индексера
        DiskIndexerFactory<String, String, String> indexerFactory = new DefaultDiskIndexerFactory();
        AbstractBlockingDiskIndexer<String, String, String> indexer = indexerFactory.buildDiskIndexer();

        //индексируем файл и создаем индекс, ищем документ
        Map<String, List<Pointer<String>>> indexData = indexer.indexForFileBlocking("src/main/resources/book1.txt");
        AbstractInvertedIndex<String, String> invertedIndex = new InvertedIndex<>(indexData);
        invertedIndex.getMostRelevantDocument("а")
                .ifPresent(System.out::println);

        //пересобираем индекс
        DefaultReindexVisitor<String, String> reIndexer = new DefaultReindexVisitor<>(new DefaultMapJoinStrategy<>());
        indexData = indexer.indexForFileBlocking("src/main/resources/book2.txt");
        invertedIndex = reIndexer.reindex(invertedIndex, indexData);
        invertedIndex.getMostRelevantDocument("а")
                .ifPresent(System.out::println);
    }
}