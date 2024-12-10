package ru.doczilla.parser;

import java.util.regex.Pattern;

public enum PathPattern {

    SIMPLE_UNIX_PATH_PATTERN_STR(
            "(?<=\\s|^)require\\s+['\"`](?<path>/?(?:[^/'\"]*/)*[^/'\"]+)['\"`]\\s+"
    );

    private final Pattern pattern;

    PathPattern(String pathPatternStr) {
        this.pattern = Pattern.compile(pathPatternStr);
    }

    public Pattern value() {
        return this.pattern;
    }
}
