package ru.surf.solution.indexer;

import ru.surf.solution.factories.signature.DiskIndexerTaskFactory;
import ru.surf.solution.index.Pointer;
import ru.surf.solution.indexer.signature.BlockingIndexer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public abstract class AbstractBlockingDiskIndexer<T, P, S> implements BlockingIndexer<T, P> {
    protected final DiskIndexerTaskFactory<T, P, S> taskFactory;
    protected final ForkJoinPool pool;

    protected AbstractBlockingDiskIndexer(DiskIndexerTaskFactory<T, P, S> taskFactory, ForkJoinPool pool) {
        this.taskFactory = taskFactory;
        this.pool = pool;
    }

    public abstract Map<T, List<Pointer<P>>> indexForFileBlocking(P path);
}
