package ru.surf.solution.factories.signature;

import ru.surf.solution.index.DiskIndexerTask;

import java.util.List;

public interface DiskIndexerTaskFactory<T, P, S> {
    DiskIndexerTask<T, P, S> buildDefaultDiskIndexerTask(P path, List<? extends S> dataToIndex);
}
