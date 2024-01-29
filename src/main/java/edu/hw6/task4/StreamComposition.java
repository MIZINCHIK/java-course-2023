package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import static java.nio.file.Files.newOutputStream;

public class StreamComposition {
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve("src/test/resources/edu/hw6/task3");

    private StreamComposition() {
        throw new IllegalStateException();
    }

    public static void writeComposeStreams(Path file) {
        try (
            Writer writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new CheckedOutputStream(
                newOutputStream(FILES.resolve(
                    file)),
                new CRC32()
            )), StandardCharsets.UTF_8))) {
            writer.write("Programming is learned by writing programs. ― Brian Kernighan");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
