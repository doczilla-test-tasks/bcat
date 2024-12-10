package ru.doczilla.graph;

import java.util.List;
import java.util.Map;

public class SimpleGraph<T> extends Graph<T> {

    public SimpleGraph() {
        super();
    }

    public SimpleGraph(Map<T, List<T>> relations) {
        super(relations);
    }
}
