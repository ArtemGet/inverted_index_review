package ru.surf.solution.index.signature;

import ru.surf.solution.index.Pointer;

import java.util.List;
import java.util.Map;

public interface IndexingStrategy<T, P, S> {
    Map<T, List<Pointer<P>>> indexLines(List<? extends S> lines);
}
