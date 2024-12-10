package ru.doczilla.cli;


import com.beust.jcommander.*;
import ru.doczilla.graph.algorithm.FindCyclesAlgorithm;
import ru.doczilla.graph.algorithm.TopologicalSort;
import ru.doczilla.parser.FileGraphBuilder;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Parameters(separators = "=")
public class Cli {

    @Parameter(
            description = "<directory-path>",
            required = true,
            converter = PathConverter.class,
            validateWith = PathValidator.class
    )
    private Path basePath;

    //TODO: add parameter to choose between name list and concatenated content output

    public static void main(String[] args) {
        try {
            var cli = new Cli();
            JCommander.newBuilder()
                    .addObject(cli)
                    .build()
                    .parse(args);

            var fileGraph = new FileGraphBuilder(cli.basePath).build();
            List<List<Path>> cycles = new FindCyclesAlgorithm<>(fileGraph).findAllCycles();

            if (!cycles.isEmpty()) {
                System.out.println("Invalid input. Cyclic requirements:");
                AtomicInteger idx = new AtomicInteger(1);
                cycles.forEach(nodeList -> {
                    System.out.printf("Cycle #%d:\n", idx.getAndIncrement());
                    nodeList.forEach(node -> System.out.println("\t" + node));
                });
                System.exit(1);
            }

            //TODO: apply topological sort on fileGraph
            var topologicallySortedVertices = new TopologicalSort<>(fileGraph).getSortedVertices(Comparator.naturalOrder());
            topologicallySortedVertices.forEach(System.out::println);
            //TODO: properly print results

        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            e.usage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}