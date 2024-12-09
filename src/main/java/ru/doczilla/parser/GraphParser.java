package ru.doczilla.parser;


import ru.doczilla.graph.Graph;

import java.io.IOException;

public interface GraphParser<T> {
    Graph<T> applyTo(T base) throws IOException;
}
