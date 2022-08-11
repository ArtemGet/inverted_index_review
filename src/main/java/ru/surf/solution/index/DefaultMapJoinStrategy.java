package ru.surf.solution.index;

import ru.surf.solution.index.signature.MapJoinStrategy;

import java.util.List;
import java.util.Map;

public class DefaultMapJoinStrategy<T, P> implements MapJoinStrategy<T, P> {
    //not implemented
    @Override
    public void joinMaps(Map<T, List<Pointer<P>>> res, Map<T, List<Pointer<P>>> toJoin) {

    }
}
