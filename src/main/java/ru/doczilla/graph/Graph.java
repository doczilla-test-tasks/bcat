package ru.doczilla.graph;

import java.util.*;
import java.util.stream.Collectors;

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

    public void addVertex(T vertex) {
        relations.put(vertex, new LinkedList<>());
    }

    public void addAllVertices(Collection<T> vertexCollection) {
        vertexCollection.forEach(this::addVertex);
    }

    public Set<T> getAlLVertices() {
        return new HashSet<>(relations.keySet());
    }

    public void addEdge(T v1, T v2) {
        //TODO: create specific Exception
        if (!relations.containsKey(v1))
            throw new RuntimeException("Vertex " + v1 + " does not exist");

        if (!relations.containsKey(v2))
            throw new RuntimeException("Vertex " + v2 + " does not exist");

        relations.get(v1).add(v2);
        relations.get(v2).add(v1);
    }

    public void addAllEdges(T v, Collection<T> relatedVertices) {
        if (!relations.containsKey(v))
            throw new RuntimeException("Vertex " + v + " does not exist");

        relations.get(v).addAll(relatedVertices);
    }

    public List<T> getAllReachableVertices(T source) {
        if (!relations.containsKey(source))
            throw new RuntimeException("Vertex " + source + " does not exist");
        return new LinkedList<>(relations.get(source));
    }

    @Override
    public String toString() {
        return "Graph {" +
                "\trelations=" + relations + ";\n" +
                '}';
    }
}
