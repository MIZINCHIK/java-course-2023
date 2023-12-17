package edu.hw10.task2.cache;

import org.jetbrains.annotations.NotNull;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiskMethodCache implements MethodCache {
    private static final String OUTPUT_EXCEPTION = "Failed to write the object to a file";
    private static final String INPUT_EXCEPTION = "Failed to read the object from the file";
    private static final String CLASS_EXCEPTION = "Failed to find the class of a serialized object";
    private static final String EXTENSION = ".object";
    private static final String SEPARATOR = "_";
    private final Path filesDirectory;

    public DiskMethodCache() {
        filesDirectory = Path.of(System.getProperty("java.io.tmpdir"));
    }

    @Override
    public boolean inCache(@NotNull Object object, @NotNull Method method, Object[] args) {
        return Files.exists(constructFilePath(object, method, args));
    }

    @Override
    public Object retrieveFromCache(@NotNull Object object, @NotNull Method method, Object[] args) {
        try (var in = new ObjectInputStream(new FileInputStream(constructFilePath(object, method, args).toFile()))) {
            return in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(INPUT_EXCEPTION, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(CLASS_EXCEPTION, e);
        }
    }

    @Override
    public void putInCache(@NotNull Object object, @NotNull Method method, Object[] args, Object result) {
        try (var out = new ObjectOutputStream(new FileOutputStream(constructFilePath(object, method, args).toFile()))) {
            out.writeObject(result);
        } catch (IOException e) {
            throw new RuntimeException(OUTPUT_EXCEPTION, e);
        }
    }

    private Path constructFilePath(Object object, Method method, Object[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append(object.hashCode()).append(SEPARATOR);
        builder.append(method.hashCode()).append(SEPARATOR);
        if (args == null) {
            builder.append("null").append(SEPARATOR);
        } else {
            for (Object arg : args) {
                builder.append(arg).append(SEPARATOR);
            }
        }
        builder.append(EXTENSION);
        return filesDirectory.resolve(builder.toString());
    }
}
