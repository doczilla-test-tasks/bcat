package ru.doczilla.cli;

import com.beust.jcommander.IStringConverter;

import java.nio.file.Path;

public class PathConverter implements IStringConverter<Path> {
    @Override
    public Path convert(String s) {
        return Path.of(s);
    }
}
