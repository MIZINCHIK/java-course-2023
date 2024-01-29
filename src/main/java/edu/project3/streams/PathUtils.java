package edu.project3.streams;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

public class PathUtils {
    private static final String USER_DIR = "user.dir";

    private PathUtils() {
        throw new IllegalStateException();
    }

    public static boolean isValidUrl(String candidate) {
        try {
            URI.create(candidate).toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Stream<Path> resolveGlob(String globPath) throws IOException {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + globPath);
        return Files.find(
            Path.of(System.getProperty(USER_DIR)),
            Integer.MAX_VALUE,
            (path, attributes) -> pathMatcher.matches(path)
        );
    }
}
