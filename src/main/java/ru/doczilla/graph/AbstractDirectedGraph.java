package ru.doczilla.graph;

import java.util.*;

public abstract class AbstractDirectedGraph<T> implements Graph<T> {

    private final Map<T, List<T>> relations;

    protected AbstractDirectedGraph(Map<T, List<T>> relationMap) {
        this.relations = relationMap;
    }

    public AbstractDirectedGraph() {
        this(new HashMap<>());
    }

    @Override
    public void addVertex(T vertex) {
        shouldNotExist(List.of(vertex));
        relations.put(vertex, new LinkedList<>());
    }

    @Override
    public void addVertices(Collection<T> vertices) {
        shouldNotExist(vertices);
        vertices.forEach(this::addVertex);
    }

    @Override
    public Set<T> vertexSet() {
        return new HashSet<>(relations.keySet());
    }

    @Override
    public void addEdge(T src, T dst) {
        shouldExist(List.of(src, dst));
        relations.get(src).add(dst);
    }

    @Override
    public void addEdges(Collection<T> src, Collection<T> dst) {
        shouldExist(src);
        shouldExist(dst);
        src.forEach(v1 -> {
            relations.get(v1).addAll(dst);
        });
    }

    @Override
    public Set<T> getAllReachableVertices(T src) {
        shouldExist(List.of(src));
        return new HashSet<>(relations.get(src));
    }

    protected void shouldExist(Collection<T> vc) {
        vc.forEach(v -> {
            if (!relations.containsKey(v))
                throw new IllegalArgumentException("Vertex " + v + " should exist.");
        });
    }

    protected void shouldNotExist(Collection<T> vc) {
        vc.forEach(v -> {
            if (relations.containsKey(v))
                throw new IllegalArgumentException("Vertex " + v + " already exists.");
        });
    }

    @Override
    public String toString() {
        return "Graph {\n" +
                "\tvertices = [\n" +
                relations.keySet()
                        .stream()
                        .map(vertex -> "\t\t'" + vertex + "',\n")
                        .reduce((s1, s2) -> s1 + s2)
                        .get() + "\n" +
                "\t];\n" +
                "\trelations = [\n" +
                relations.entrySet().stream().flatMap(entry ->
                                entry.getValue()
                                        .stream()
                                        .map(el -> "\t\t('" + entry.getKey() + "' ==> '" + el + "'),\n"))
                        .reduce((s1, s2) -> s1 + s2)
                        .get() +
                "\t];\n" +
                "};";
    }
}
