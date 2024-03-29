package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileCloner {
    private static final String INCORRECT_PATH = "Couldn't find the file by the path";

    private FileCloner() {
        throw new IllegalStateException();
    }

    public static boolean cloneFile(Path file) {
        if (file == null || !Files.isRegularFile(file)) {
            return false;
        }
        try {
            Files.copy(file, constructCloneName(file));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static Path constructCloneName(Path file) {
        Objects.requireNonNull(file);
        if (!Files.isRegularFile(file) || !Files.exists(file)) {
            throw new IllegalArgumentException(INCORRECT_PATH);
        }
        String fileName = file.getFileName().toString();
        String[] tokens = getNameExtension(fileName);
        String fileNoExtension = tokens[0];
        String extension = tokens.length > 1 ? tokens[1] : "";
        String clone = fileNoExtension + " - копия" + extension;
        Path directory = file.getParent();
        int index = 2;
        while (Files.exists(directory.resolve(clone))) {
            clone = fileNoExtension + " - копия (" + index++ + ")" + extension;
        }
        return directory.resolve(clone);
    }

    private static String[] getNameExtension(String fileName) {
        return fileName.split("\\.(?=[^.]*$)");
    }
}
