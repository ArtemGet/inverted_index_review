package ru.surf.solution.index;

public class Pointer<P> {
    private Long count;
    private final P sourcePath;

    public Pointer(Long count, P sourcePath) {
        this.count = count;
        this.sourcePath = sourcePath;
    }

    public Pointer(P sourcePath) {
        this.count = 1L;
        this.sourcePath = sourcePath;
    }

    void incrementBy(Long count) {
        this.count += count;
    }

    public Long getCount() {
        return count;
    }

    public P getSourcePath() {
        return sourcePath;
    }
}