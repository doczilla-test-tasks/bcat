package ru.doczilla;


import com.beust.jcommander.*;

import java.nio.file.Files;
import java.nio.file.Path;


@Parameters(separators = "=")
public class Cli {

    @Parameter(description = "<directory-path>", required = true, converter = PathConverter.class)
    private Path basePath;

    static class PathConverter implements IStringConverter<Path> {
        @Override
        public Path convert(String s) {
            Path res = Path.of(s);
            if (!Files.exists(res)) {
                throw new ParameterException("Path " + s + " does not exist. Existing path required.");
            }
            return res;
        }
    }

    public static void main(String[] args) {
        try {
            Cli cli = new Cli();
            JCommander.newBuilder()
                    .addObject(cli)
                    .build()
                    .parse(args);


        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            e.usage();
        }
    }
}