package ru.surf.solution.indexer.signature;

import java.io.IOException;
import java.util.List;

public interface DiskIndexerDataReaderStrategy<P,S> {
    List<? extends S> readData(P path) throws IOException;
}
