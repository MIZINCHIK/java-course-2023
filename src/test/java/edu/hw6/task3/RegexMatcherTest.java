package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task3.RegexMatcher.regexContains;
import static org.assertj.core.api.Assertions.assertThat;

public class RegexMatcherTest {
    private static final String RESOURCES_FROM_ROOT = "src/test/resources/edu/hw6/task3";
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve(RESOURCES_FROM_ROOT);

    @Test
    @DisplayName("Match files with underscores")
    void regexContains_acceptUnderscore_correctFiles() {
        List<Path> expected =
            List.of(Path.of("Logo_Team_Tinkoff.png"), Path.of("754be574a5d44a28_d2021ea4827dd834.png"));
        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     FILES,
                     regexContains("^.*[_].*$")
                 )) {
            entries.forEach(entry -> fileNames.add(entry.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileNames).containsExactlyInAnyOrderElementsOf(expected);
    }
}
