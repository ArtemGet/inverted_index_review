package ru.surf.solution.indexer.signature;

import ru.surf.solution.index.Pointer;

import java.util.List;
import java.util.Map;

public interface BlockingIndexer<T, P> {
    Map<T, List<Pointer<P>>> indexForPathBlocking(P path);
}
