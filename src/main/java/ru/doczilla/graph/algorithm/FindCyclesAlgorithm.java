package ru.doczilla.graph.algorithm;

import ru.doczilla.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindCyclesAlgorithm<T> {

    enum Color {
        WHITE,
        GREY,
        BLACK
    }

    private final Graph<T> graph;
    private final Map<T, Color> colors;

    public FindCyclesAlgorithm(Graph<T> graph) {
        this.graph = graph;
        this.colors = new HashMap<>();
    }

    private List<T> dfs(T vertex) {
        try {

            colors.put(vertex, Color.GREY);
            return graph.getAllReachableVertices(vertex)
                    .stream().flatMap(v -> {
                        if (colors.get(v) == Color.WHITE) {
                            List<T> list = dfs(v);
                            list.add(v);
                            return list.stream();
                        } else if (colors.get(v) == Color.GREY) {
                            return Stream.of(v);
                        }
                        return Stream.of();
                    }).collect(Collectors.toCollection(LinkedList::new));
        } finally {
            colors.put(vertex, Color.BLACK);
        }
    }

    public List<List<T>> findAllCycles() {
        Set<T> vertices = graph.vertexSet();
        vertices.forEach(v -> colors.put(v, Color.WHITE));
        List<List<T>> res = new LinkedList<>();
        while (!vertices.isEmpty()) {
            T currentVertex = vertices.iterator().next();
            List<T> dfsRes = dfs(currentVertex);
            if (dfsRes.size() > 1) {
                res.add(dfsRes.reversed());
                vertices.removeAll(new HashSet<>(dfsRes));
            } else {
                vertices.remove(currentVertex);
            }
        }
        return res;
    }

    public Graph<T> getSourceGraph() {
        return this.graph;
    }
}
