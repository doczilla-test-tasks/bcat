package ru.doczilla.graph;

import java.util.Collection;
import java.util.Set;

public interface Graph<T> {
    void addVertex(T v);
    void addVertices(Collection<T> vertices);

    Set<T> vertexSet();

    void addEdge(T v1, T v2);
    void addEdges(Collection<T> vc1, Collection<T> vc2);

    Set<T> getAllReachableVertices(T src);
}
