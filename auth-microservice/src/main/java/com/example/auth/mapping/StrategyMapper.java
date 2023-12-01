package com.example.auth.mapping;

import java.util.List;

public interface StrategyMapper<S, D> {
    D map(S source);
    List<D> mapAll(List<S> source);
}
