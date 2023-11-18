package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task3.GlobMatcher.globMatches;
import static org.assertj.core.api.Assertions.assertThat;

public class GlobMatcherTest {
    private static final String RESOURCES_FROM_ROOT = "src/test/resources/edu/hw6/task3";
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve(RESOURCES_FROM_ROOT);

    @Test
    @DisplayName("Matching jpeg with glob")
    void globMatches_acceptJpeg_onePicture() {
        List<Path> expected = List.of(Path.of("chchh.jpeg"));
        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(FILES, globMatches("*.jpeg"))) {
            entries.forEach(entry -> fileNames.add(entry.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileNames).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Matching png with glob")
    void globMatches_acceptPng_twoPictures() {
        List<Path> expected =
            List.of(
                Path.of("Logo_Team_Tinkoff.png"),
                Path.of("sadsadsa.png"),
                Path.of("754be574a5d44a28_d2021ea4827dd834.png"));
        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(FILES, globMatches("*.png"))) {
            entries.forEach(entry -> fileNames.add(entry.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileNames).containsExactlyInAnyOrderElementsOf(expected);
    }
}
