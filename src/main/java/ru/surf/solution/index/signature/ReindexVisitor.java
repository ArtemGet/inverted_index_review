package ru.surf.solution.index.signature;

import ru.surf.solution.index.AbstractInvertedIndex;
import ru.surf.solution.index.Pointer;

import java.util.List;
import java.util.Map;

public interface ReindexVisitor<P, T> {

    AbstractInvertedIndex<P, T> reindex(AbstractInvertedIndex<P, T> invertedIndex,
                                        Map<T, List<Pointer<P>>> addOnData);
}
