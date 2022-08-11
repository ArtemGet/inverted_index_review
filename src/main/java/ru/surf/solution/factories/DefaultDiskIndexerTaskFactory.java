package ru.surf.solution.factories;

import ru.surf.solution.factories.signature.DiskIndexerTaskFactory;
import ru.surf.solution.index.DefaultIndexingStrategy;
import ru.surf.solution.index.DefaultMapJoinStrategy;
import ru.surf.solution.index.DiskIndexerTask;
import ru.surf.solution.utils.AppConstants;

import java.util.List;

public class DefaultDiskIndexerTaskFactory implements DiskIndexerTaskFactory<String, String, String> {
    @Override
    public DiskIndexerTask<String, String, String> buildDefaultDiskIndexerTask(String path, List<? extends String> dataToIndex) {
        int availableCores = Runtime.getRuntime().availableProcessors();
        return new DiskIndexerTask<>(
                new DefaultMapJoinStrategy<>(),
                new DefaultIndexingStrategy<>(),
                dataToIndex,
                path,
                availableCores * AppConstants.DEFAULT_WORKERS_COEFFICIENT);
    }
}
