package ru.doczilla.cli;


import com.beust.jcommander.*;
import ru.doczilla.graph.algorithm.FindCyclesAlgorithm;
import ru.doczilla.graph.algorithm.TopologicalSort;
import ru.doczilla.parser.DirectoryParser;

import java.nio.file.Path;
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

            var fileGraph = new DirectoryParser(cli.basePath).getGraph();
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

            var topologicalSortAlgorithm = new TopologicalSort<>(fileGraph);
            topologicalSortAlgorithm.getSortedVertices().forEach(System.out::println);

            //TODO: make output variable (related to output mods)

        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            e.usage();
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getCause() != null)
                System.out.println(e.getCause().getMessage());
        }
    }
}