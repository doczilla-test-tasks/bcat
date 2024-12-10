package ru.doczilla.parser;

import ru.doczilla.graph.Graph;
import ru.doczilla.graph.GraphBuilder;
import ru.doczilla.graph.SimpleDirectedGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DirectoryParser implements GraphParser<Path> {

    private final Path basePath;
    private final Pattern pathPattern;
    private final Graph<Path> graph;

    public DirectoryParser(Path basepath, Pattern pattern) {
        this.basePath = basepath;
        this.pathPattern = pattern;

        var filePaths = getFilePaths(this.basePath);
        var graphBuilder = new SimpleDirectedGraph
                .SimpleDirectedGraphBuilder<Path>()
                .withVertices(filePaths);
        filePaths.forEach(p -> graphBuilder.withEdges(List.of(p), getFileDependencies(p)));
        this.graph = graphBuilder.build();
    }

    public DirectoryParser(Path basePath) {
        this(basePath, PathPattern.SIMPLE_UNIX_PATH_PATTERN_STR.value());
    }

    private List<Path> getFilePaths(Path p) {
        try (Stream<Path> pStream = Files.list(p)) {
            return pStream.flatMap(path -> {
                if (Files.isDirectory(path))
                    return getFilePaths(path).stream();
                else
                    return Stream.of(path.toAbsolutePath().normalize());
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException("Something occurred during directory '" + p + "' parsing.", e);
        }
    }

    private List<Path> getFileDependencies(Path p) {
        try (BufferedReader br = Files.newBufferedReader(p)) {
            Optional<String> fileContentOptional = br.lines().reduce((s1, s2) -> s1 + "\n" + s2);
            if (fileContentOptional.isEmpty())
                return new LinkedList<>();
            String fileContent = fileContentOptional.get();

            Matcher matcher = pathPattern.matcher(fileContent);
            List<Path> res = new LinkedList<>();
            while (matcher.find()) {
                Path relationPath = Path.of(basePath.toString(), matcher.group("path")).toAbsolutePath().normalize();
                if (!Files.exists(relationPath)) {
                    throw new RuntimeException("File '" + relationPath + "' does not exist");
                }

                res.add(relationPath);
            }

            return res;
        } catch (IOException e) {
            throw new RuntimeException("Something occurred during file '" + p + "' reading", e);
        }
    }

    @Override
    public void updateGraph() {
        //TODO: implement
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Graph<Path> getGraph() {
        return this.graph;
    }
}
