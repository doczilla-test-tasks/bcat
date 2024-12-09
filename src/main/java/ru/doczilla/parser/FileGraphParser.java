package ru.doczilla.parser;

import ru.doczilla.graph.Graph;
import ru.doczilla.graph.SimpleGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileGraphParser implements GraphParser<Path>{

    private List<Path> getFilePaths(Path p) {
        try (Stream<Path> pStream = Files.list(p)) {
            return pStream.flatMap(path -> {
                if (Files.isDirectory(path))
                    return getFilePaths(path).stream();
                else
                    return Stream.of(path.toAbsolutePath().normalize());
            }).toList();
        } catch (IOException e) {
            //TODO: create specific exception
            throw new RuntimeException();
        }
    }

    @Override
    public Graph<Path> applyTo(Path base) throws IOException {
        Graph<Path> graph = new SimpleGraph<>();
        var filePaths = getFilePaths(base);

        filePaths.forEach(System.out::println);

        return null;
    }
}
