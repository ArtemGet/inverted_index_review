# Тестовое задание:    
Делаем вид, что код вам прислал стажер    
###Нужно:   
Провести ревью   
Сказать, что делает код   

## Провести ревью:    
Код с ревью находится в пакете intern_source_code    
Советы и источники:    
```
1)Посмотреть что такое FJP - https://www.youtube.com/watch?v=t0dGLFtRR9c&t=4557s&ab_channel=JPoint%2CJoker%D0%B8JUGru    
https://habr.com/ru/post/565924/    
2)Почитать про модификаторы доступа, format convention - https://www.cs.cornell.edu/courses/JavaAndDS/JavaStyle.html    
3)Стараться избегать сайд эффектов    
4)Почитать про InterruptedException и закрытие пула потоков - https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html    
https://www.yegor256.com/2015/10/20/interrupted-exception.html    
```
Примерная архитектура находится в папке solution, пример использования в solution/Main. 
Детали стратегий не реализованны, т.к это не было частью задания.

## Сказать, что делает код:
Данный код - наивная реализация обратного индекса для полнотекстового поиска.
Есть возможность проиндексировать директорию, получить документы в которые входит какое-либо слово и 
получить самый релевантный файл.