package ru.doczilla.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Graph<T> {

    private final Map<T, List<T>> relations;

    public Graph(Map<T, List<T>> relationMap) {
        this.relations = relationMap;
    }

    public Graph(Graph<T> otherGraph) {
        //TODO: think more about copying relations HashMap
        this.relations = otherGraph.relations;
    }

    public Graph() {
        this(new HashMap<>());
    }

    public Map<T, List<T>> getRelations() {
        return relations;
    }
}
