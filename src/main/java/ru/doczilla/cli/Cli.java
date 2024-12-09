package ru.doczilla.cli;


import com.beust.jcommander.*;
import ru.doczilla.parser.FileGraphParser;

import java.io.IOException;
import java.nio.file.Path;


@Parameters(separators = "=")
public class Cli {

    @Parameter(
            description = "<directory-path>",
            required = true,
            converter = PathConverter.class,
            validateWith = PathValidator.class
    )
    private Path basePath;

    public static void main(String[] args) {
        try {
            var cli = new Cli();
            JCommander.newBuilder()
                    .addObject(cli)
                    .build()
                    .parse(args);

            var fileGraph = new FileGraphParser().applyTo(cli.basePath);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            e.usage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}