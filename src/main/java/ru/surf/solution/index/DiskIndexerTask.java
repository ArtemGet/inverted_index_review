package ru.surf.solution.index;

import ru.surf.solution.index.signature.IndexingStrategy;
import ru.surf.solution.index.signature.MapJoinStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class DiskIndexerTask<T, P, S> extends RecursiveTask<Map<T, List<Pointer<P>>>> {
    private final MapJoinStrategy<T, P> joinStrategy;
    private final IndexingStrategy<T, P, S> indexingStrategy;

    private final List<? extends S> toIndex;
    private final P fromPath;
    private final Integer threshold;

    public DiskIndexerTask(MapJoinStrategy<T, P> joinStrategy,
                           IndexingStrategy<T, P, S> indexingStrategy,
                           List<? extends S> toIndex,
                           P fromPath,
                           Integer threshold) {
        this.joinStrategy = joinStrategy;
        this.indexingStrategy = indexingStrategy;
        this.toIndex = toIndex;
        this.fromPath = fromPath;
        this.threshold = threshold;
    }

    @Override
    protected Map<T, List<Pointer<P>>> compute() {
        Map<T, List<Pointer<P>>> res = new HashMap<>();
        if (toIndex.size() <= threshold) {
            return indexingStrategy.indexLines(toIndex);
        }

        DiskIndexerTask<T,P,S> leftSide = new DiskIndexerTask<>(
                this.joinStrategy,
                this.indexingStrategy,
                toIndex.subList(0, toIndex.size() / 2),
                this.fromPath,
                this.threshold);
        DiskIndexerTask<T,P,S> rightSide = new DiskIndexerTask<>(
                this.joinStrategy,
                this.indexingStrategy,
                toIndex.subList(toIndex.size() / 2, toIndex.size()),
                this.fromPath,
                this.threshold);
        ForkJoinTask.invokeAll(leftSide, rightSide);

        joinStrategy.joinMaps(res, rightSide.join());
        joinStrategy.joinMaps(res, leftSide.join());
        return res;
    }
}
