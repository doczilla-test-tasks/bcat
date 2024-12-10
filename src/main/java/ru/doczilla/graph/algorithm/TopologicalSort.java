package ru.doczilla.graph.algorithm;

import ru.doczilla.graph.Graph;

import java.util.*;

public class TopologicalSort<T> {

    private final Graph<T> graph;
    private final Set<T> used;
    private final Map<Integer, List<T>> order;

    public TopologicalSort(Graph<T> graph) {
        this.graph = graph;
        this.used = new HashSet<>();
        this.order = new HashMap<>();
    }

    private void dfs(T vertex) {
        used.add(vertex);
        graph.getAllReachableVertices(vertex)
                .forEach(v -> {
                    if (!used.contains(v))
                        dfs(v);
                });
        int sz = graph.getAllReachableVertices(vertex).size();
        if (!order.containsKey(sz))
            order.put(sz, new ArrayList<>());
        order.get(sz).add(vertex);
    }

    public List<T> getSortedVertices(Comparator<T> comparator) {
        Set<T> vertices = graph.getAlLVertices();
        for (T vertex : vertices) {
            if (!used.contains(vertex))
                dfs(vertex);
        }

        List<T> res = new LinkedList<>();
        for (
                int i = order.keySet().stream().min(Comparator.naturalOrder()).get();
                i <= order.keySet().stream().max(Comparator.naturalOrder()).get();
                ++i
        ) {
            order.get(i).sort(comparator);
            res.addAll(order.get(i));
        }

        return res;
    }

    public Graph<T> getSourceGraph() {
        return this.graph;
    }
}
