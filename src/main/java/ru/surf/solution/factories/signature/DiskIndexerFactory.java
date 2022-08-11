package ru.surf.solution.factories.signature;

import ru.surf.solution.indexer.AbstractBlockingDiskIndexer;

public interface DiskIndexerFactory<T, P, S> {
    AbstractBlockingDiskIndexer<T, P, S> buildDiskIndexer();
}
