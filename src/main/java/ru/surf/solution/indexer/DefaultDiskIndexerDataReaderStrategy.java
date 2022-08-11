package ru.surf.solution.indexer;

import ru.surf.solution.indexer.signature.DiskIndexerDataReaderStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DefaultDiskIndexerDataReaderStrategy implements DiskIndexerDataReaderStrategy<String, String> {
    public List<String> readData(String path) throws IOException {
        return Files.readAllLines(Path.of(path));
    }
}
