package edu.project3.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static edu.project3.streams.PathUtils.isValidUrl;
import static edu.project3.streams.PathUtils.resolveGlob;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PathUtilsTest {
    @Test
    @DisplayName("Invalid URLS")
    void isValidUrl_invalidUrl_false() {
        assertThat(isValidUrl("sadsadsad")).isFalse();
        assertThat(isValidUrl("192.168.0.1")).isFalse();
        assertThat(isValidUrl("google.com")).isFalse();
    }

    @Test
    @DisplayName("Valid URLS")
    void isValidUrl_validUrl_true() {
        assertThat(isValidUrl("https://github.com/")).isTrue();
        assertThat(isValidUrl("https://edu.tinkoff.ru")).isTrue();
        assertThat(isValidUrl("https://en.wiktionary.org/wiki/guaran%C3%AD#Old_Tupi")).isTrue();
    }

    @Test
    @DisplayName("Incorrect glob patterns")
    void resolveGlob_incorrectPattern_emptyStream() throws IOException {
        assertThat(resolveGlob("https://en.wiktionary.org/wiki/guaran%C3%AD#Old_Tupi").findAny().isEmpty()).isTrue();
        assertThat(resolveGlob("sadadsadsad").findAny().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Correct glob patterns")
    void resolveGlob_correctPattern_respectivePathsInStream() throws IOException {
        Path files = Path.of(System.getProperty("user.dir"))
            .resolve("src/test/resources/edu/project3/");
        Files.createDirectories(files);
        var file1 = Files.createFile(files.resolve("test1.txt"));
        var file2 = Files.createFile(files.resolve("test2.txt"));
        var file3 = Files.createFile(files.resolve("test3.txt"));
        List<Path> expected = List.of(file1, file2, file3);
        List<Path> real = resolveGlob(files.resolve("test*.txt").toString()).toList();
        assertThat(expected.size() == real.size() && expected.containsAll(real) && real.containsAll(expected)).isTrue();
        Files.delete(file1);
        Files.delete(file2);
        Files.delete(file3);
    }
}
