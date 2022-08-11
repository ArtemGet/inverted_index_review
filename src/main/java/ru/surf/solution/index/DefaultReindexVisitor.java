package ru.surf.solution.index;

import ru.surf.solution.index.signature.MapJoinStrategy;
import ru.surf.solution.index.signature.ReindexVisitor;

import java.util.List;
import java.util.Map;

public class DefaultReindexVisitor<P, T> implements ReindexVisitor<P, T> {
    private final MapJoinStrategy<T, P> mapJoinStrategy;

    public DefaultReindexVisitor(MapJoinStrategy<T, P> mapJoinStrategy) {
        this.mapJoinStrategy = mapJoinStrategy;
    }

    @Override
    public AbstractInvertedIndex<P, T> reindex(AbstractInvertedIndex<P, T> invertedIndex, Map<T, List<Pointer<P>>> addOnData) {
        mapJoinStrategy.joinMaps(invertedIndex.invertedIndex, addOnData);
        return invertedIndex;
    }
}
