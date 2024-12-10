package ru.doczilla.parser;

import ru.doczilla.graph.Graph;
import ru.doczilla.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileGraphBuilder implements GraphBuilder<Path> {

    //TODO: create special enum with such patterns to be sure that "path" capture group is present
    private static final String SIMPLE_UNIX_PATH_PATTERN_STR =
            "(?<=;|^)\\s*require\\s+['\"`](?<path>/?(?:[^/'\"]*/)*[^/'\"]+)['\"`]\\s*;(?=\\s*)";

    private final Path basePath;
    private final Pattern pathPattern;

    public FileGraphBuilder(Path basepath, Pattern pattern) {
        this.basePath = basepath;
        this.pathPattern = pattern;
    }

    public FileGraphBuilder(Path basePath) {
        this(basePath, Pattern.compile(SIMPLE_UNIX_PATH_PATTERN_STR));
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
            //TODO: create specific exception
            throw new RuntimeException(e);
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
                    throw new RuntimeException();
                }

                res.add(relationPath);
            }

            return res;
        } catch (IOException e) {
            //TODO: create specific exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public Graph<Path> build() throws IOException {
        Graph<Path> graph = new SimpleGraph<>();
        var filePaths = getFilePaths(this.basePath);

        graph.addAllVertices(filePaths);
        filePaths.forEach(p -> {
            List<Path> dependencies = getFileDependencies(p);
            graph.addAllEdges(p, dependencies);
        });

        return graph;
    }
}
