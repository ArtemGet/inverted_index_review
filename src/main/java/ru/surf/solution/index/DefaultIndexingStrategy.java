package ru.surf.solution.index;

import ru.surf.solution.index.signature.IndexingStrategy;

import java.util.List;
import java.util.Map;

public class DefaultIndexingStrategy<T, P, S> implements IndexingStrategy<T, P, S> {

    //not implemented
    @Override
    public Map<T, List<Pointer<P>>> indexLines(List<? extends S> lines) {
        return null;
    }
}
