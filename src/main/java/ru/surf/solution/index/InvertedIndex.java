package ru.surf.solution.index;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InvertedIndex<P, T> extends AbstractInvertedIndex<P, T> {

    public InvertedIndex(Map<T, List<Pointer<P>>> invertedIndex) {
        super(invertedIndex);
    }

    @Override
    public List<Pointer<P>> getRelevantDocuments(T term) {
        return super.getInvertedIndex().get(term);
    }

    @Override
    public Optional<Pointer<P>> getMostRelevantDocument(T term) {
        return invertedIndex.get(term).stream()
                .max(Comparator.comparing(Pointer::getCount));
    }
}
