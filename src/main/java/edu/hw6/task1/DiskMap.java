package edu.hw6.task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap extends HashMap<String, String> {
    public static final String SUB_DIRECTORY_NAME = "kv_storage";
    public static final String EXTENSION = ".kv";
    private static final String KV_FILE_FORMAT = "^.*" + EXTENSION + "$";
    private final Path path;

    public DiskMap(Path path) throws IOException, IllegalArgumentException {
        if (path == null || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Incorrect path");
        }
        this.path = path.resolve(SUB_DIRECTORY_NAME);
        Files.createDirectories(this.path);
        loadKeyValues();
    }

    private void loadKeyValues() throws IOException {
        try (var directoryStream = Files.newDirectoryStream(path)) {
            for (Path file : directoryStream) {
                loadKeyValue(file);
            }
        }
    }

    private void loadKeyValue(Path file) throws IOException {
        String fileName = file.getFileName().toString();
        if (Files.isRegularFile(file)
            && fileName.matches(KV_FILE_FORMAT)) {
            put(fileName.substring(0, fileName.lastIndexOf(".")), Files.readString(file));
        }
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        Path keyValue = path.resolve(key + EXTENSION);
        try {
            Files.createFile(keyValue);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            throw new KeyValueConsistencyException("Something happened to storage directory", e);
        }
        try {
            Files.writeString(keyValue, value, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.put(key, value);
        return null;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof String)) {
            return null;
        }
        Path keyValue = path.resolve(key + EXTENSION);
        if (!Files.exists(keyValue) || !Files.isRegularFile(keyValue)) {
            if (super.containsKey(key)) {
                throw new KeyValueConsistencyException("Existing key was deleted prior");
            }
            return null;
        }
        try {
            Files.delete(keyValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (var walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.clear();
    }
}
