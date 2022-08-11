package ru.surf.solution.factories;

import ru.surf.solution.factories.signature.DiskIndexerFactory;
import ru.surf.solution.indexer.AbstractBlockingDiskIndexer;
import ru.surf.solution.indexer.DefaultDiskIndexer;
import ru.surf.solution.indexer.DefaultDiskIndexerDataReaderStrategy;

import java.util.concurrent.ForkJoinPool;

public class DefaultDiskIndexerFactory implements DiskIndexerFactory<String, String, String> {

    @Override
    public AbstractBlockingDiskIndexer<String, String, String> buildDiskIndexer() {
        return new DefaultDiskIndexer<>(
                new ForkJoinPool(),
                new DefaultDiskIndexerDataReaderStrategy(),
                new DefaultDiskIndexerTaskFactory()
        );
    }
}
