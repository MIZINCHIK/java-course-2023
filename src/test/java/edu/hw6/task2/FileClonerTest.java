package edu.hw6.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task2.FileCloner.cloneFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileClonerTest {
    private static final String RESOURCES_FROM_ROOT = "src/test/resources/edu/hw6/task2";
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve(RESOURCES_FROM_ROOT);

    @AfterEach
    void restoreDirectory() {
        try (var walk = Files.walk(FILES)) {
            walk.sorted(Comparator.reverseOrder())
                .filter(file -> !file.getFileName().toString().equals("anchor"))
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Cloning a file")
    void cloneFile_correctPathNoClonesInDir_fileCloned() throws IOException {
        Path file = FILES.resolve("file");
        Files.createFile(file);
        cloneFile(file);
        assertThat(Files.exists(FILES.resolve("file - копия"))).isTrue();
    }

    @Test
    @DisplayName("Cloning a file in a directory with other clones")
    void cloneFile_correctPathAreClonesInDir_fileCloned() throws IOException {
        Path file = FILES.resolve("file");
        Files.createFile(file);
        Files.createFile(FILES.resolve("file - копия"));
        Files.createFile(FILES.resolve("file - копия (2)"));
        Files.createFile(FILES.resolve("file - копия (3)"));
        Files.createFile(FILES.resolve("file - копия (4)"));
        Files.createFile(FILES.resolve("file - копия (6)"));
        Files.createFile(FILES.resolve("file - копия (7)"));
        Files.createFile(FILES.resolve("file - копия (8)"));
        Files.createFile(FILES.resolve("file - копия (9)"));
        assertThat(cloneFile(file)).isTrue();
        assertThat(Files.exists(FILES.resolve("file - копия (5)"))).isTrue();
    }

    @Test
    @DisplayName("Incorrect path")
    void cloneFile_incorrectPath_false() {
        Path file = Path.of("safjhksdjhfksf");
        assertThat(cloneFile(file)).isFalse();
    }

    @Test
    @DisplayName("Cloning a clone")
    void cloneFile_cloneOfClone_multipleSuffix() throws IOException {
        Path file = FILES.resolve("file");
        Files.createFile(file);
        cloneFile(file);
        Path clone = FILES.resolve("file - копия");
        assertThat(cloneFile(clone)).isTrue();
        assertThat(Files.exists(FILES.resolve("file - копия - копия"))).isTrue();
    }
}
