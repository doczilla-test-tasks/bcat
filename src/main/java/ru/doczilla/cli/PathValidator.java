package ru.doczilla.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        Path p = Path.of(value);
        if (!Files.exists(p))
            throw new ParameterException("Path " + value + " does not exist. Existing path required.");
        if (!Files.isDirectory(p))
            throw new ParameterException("Path " + value + " must be a directory. Non-directory path given.");
    }
}
