package ru.surf.solution.index.signature;

import ru.surf.solution.index.Pointer;

import java.util.List;
import java.util.Map;

public interface MapJoinStrategy<T,P> {
    void joinMaps(Map<T, List<Pointer<P>>> res,
                  Map<T, List<Pointer<P>>> toJoin);
}
