package ru.surf.solution.index.signature;

import ru.surf.solution.index.Pointer;

import java.util.List;
import java.util.Optional;

public interface TermSearcher<P, T> {
    List<? extends Pointer<P>> getRelevantDocuments(T term);

    Optional<? extends Pointer<P>> getMostRelevantDocument(T term);
}
