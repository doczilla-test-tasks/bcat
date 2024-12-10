package ru.doczilla.parser;


import ru.doczilla.graph.Graph;

import java.io.IOException;

public interface GraphBuilder<T> {
    Graph<T> build() throws IOException;
}
