package ru.surf.solution.indexer;

import ru.surf.solution.exception.TaskExecutionException;
import ru.surf.solution.factories.signature.DiskIndexerTaskFactory;
import ru.surf.solution.index.DiskIndexerTask;
import ru.surf.solution.index.Pointer;
import ru.surf.solution.indexer.signature.DiskIndexerDataReaderStrategy;
import ru.surf.solution.utils.ExecutorUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class DefaultDiskIndexer<T, P, S> extends AbstractBlockingDiskIndexer<T, P, S> {
    private final DiskIndexerDataReaderStrategy<P, S> readerStrategy;

    public DefaultDiskIndexer(ForkJoinPool pool,
                              DiskIndexerDataReaderStrategy<P, S> readerStrategy,
                              DiskIndexerTaskFactory<T, P, S> taskFactory) {
        super(taskFactory, pool);
        this.readerStrategy = readerStrategy;
    }

    //Not implemented
    @Override
    public Map<T, List<Pointer<P>>> indexForPathBlocking(P path) {
        return null;
    }

    @Override
    public Map<T, List<Pointer<P>>> indexForFileBlocking(P path) {
        try {
            List<? extends S> dataToIndex = readerStrategy.readData(path);
            DiskIndexerTask<T, P, S> indexingTask = taskFactory.buildDefaultDiskIndexerTask(path, dataToIndex);
            return pool.invoke(indexingTask);
        } catch (TaskExecutionException | IOException e) {
            ExecutorUtils.shutdownAndAwaitTermination(this.pool);
            throw new TaskExecutionException(e);
        }
    }
}
