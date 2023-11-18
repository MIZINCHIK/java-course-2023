package edu.hw6.task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task4.StreamComposition.writeComposeStreams;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StreamCompositionTest {
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve("src/test/resources/edu/hw6/task4");
    private static final Path FILE = FILES.resolve("test.txt");

    @Test
    @DisplayName("Creating and writing to a file with writeComposeStreams")
    void writeComposeStreams_nonExistentFile_stringWritten() throws IOException {
        writeComposeStreams(FILE);
        assertThat(Files.readString(FILE)).isEqualTo("Programming is learned by writing programs. ― Brian Kernighan");
        Files.delete(FILE);
    }
}
