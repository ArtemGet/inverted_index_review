package ru.surf.solution.index;

import ru.surf.solution.index.signature.TermSearcher;

import java.util.List;
import java.util.Map;

public abstract class AbstractInvertedIndex<P, T> implements TermSearcher<P, T> {
    final Map<T, List<Pointer<P>>> invertedIndex;

    protected AbstractInvertedIndex(Map<T, List<Pointer<P>>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    Map<? extends T, ? extends List<Pointer<P>>> getInvertedIndex() {
        return invertedIndex;
    }
}