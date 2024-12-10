package ru.doczilla.graph;

import java.util.Collection;

public interface GraphBuilder<T> {
    Graph<T> build();
    GraphBuilder<T> withVertex(T vertex);
    GraphBuilder<T> withVertices(Collection<T> vertices);
    GraphBuilder<T> withEdge(T v1, T v2);
    GraphBuilder<T> withEdges(Collection<T> vc1, Collection<T> vc2);
}
