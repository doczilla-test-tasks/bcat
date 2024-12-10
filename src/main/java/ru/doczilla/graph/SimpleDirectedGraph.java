package ru.doczilla.graph;

import java.util.*;

public class SimpleDirectedGraph<T> extends AbstractDirectedGraph<T> {

    protected SimpleDirectedGraph(Map<T, List<T>> relations) {
        super(relations);
    }

    public SimpleDirectedGraph() {
        super();
    }

    public static class SimpleDirectedGraphBuilder<T> implements GraphBuilder<T> {

        private final AbstractDirectedGraph<T> graph;

        public SimpleDirectedGraphBuilder() {
            this.graph = new SimpleDirectedGraph<>();
        }

        @Override
        public GraphBuilder<T> withVertex(T vertex) {
            graph.addVertex(vertex);
            return this;
        }

        @Override
        public GraphBuilder<T> withVertices(Collection<T> vertices) {
            graph.addVertices(vertices);
            return this;
        }

        @Override
        public GraphBuilder<T> withEdge(T src, T dst) {
            graph.addEdge(src, dst);
            return this;
        }

        @Override
        public GraphBuilder<T> withEdges(Collection<T> src, Collection<T> dst) {
            graph.addEdges(src, dst);
            return this;
        }

        public AbstractDirectedGraph<T> build() {
            return this.graph;
        }
    }
}
