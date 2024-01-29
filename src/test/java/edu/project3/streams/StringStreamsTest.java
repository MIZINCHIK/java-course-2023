package edu.project3.streams;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import static edu.project3.streams.StringStreams.getFileLineByLineStream;
import static java.lang.System.lineSeparator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class StringStreamsTest {
    private static final Path files = Path.of(System.getProperty("user.dir"))
        .resolve("src/test/resources/edu/project3/");

    @BeforeAll
    static void preparePath() throws IOException {
        Files.createDirectories(files);
    }

    @AfterAll
    static void purgeFiles() throws IOException {
        try (var stream = Files.walk(files)) {
            stream
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }

    @Test
    @DisplayName("Streaming empty file in strings")
    void getFileLineByLineStream_emptyFile_emptyStream() throws IOException {
        var testFile = files.resolve("test.txt");
        Files.createFile(testFile);
        assertIterableEquals(Stream.empty().toList(), getFileLineByLineStream(testFile).toList());
    }

    @Test
    @DisplayName("Streaming normal file in strings")
    void getFileLineByLineStream_normalFile_allLines() throws IOException {
        var testFile = files.resolve("test2.txt");
        Files.createFile(testFile);
        Files.writeString(testFile, "asdsadsad" + lineSeparator() + "sadsad" + lineSeparator() + "ssa");
        assertIterableEquals(List.of("asdsadsad", "sadsad", "ssa"), getFileLineByLineStream(testFile).toList());
    }
}
