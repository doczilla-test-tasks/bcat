package ru.doczilla.parser;

import ru.doczilla.graph.Graph;

public interface GraphParser<T> {
    void updateGraph();
    Graph<T> getGraph();
}
