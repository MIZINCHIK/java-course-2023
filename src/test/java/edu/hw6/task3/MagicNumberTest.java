package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task3.MagicNumber.magicNumber;
import static org.assertj.core.api.Assertions.assertThat;

public class MagicNumberTest {
    private static final String RESOURCES_FROM_ROOT = "src/test/resources/edu/hw6/task3";
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve(RESOURCES_FROM_ROOT);

    @Test
    @DisplayName("Match files with PNG headers")
    void magicNumber_acceptPngHeader_correctPngs() {
        List<Path> expected =
            List.of(Path.of("Logo_Team_Tinkoff.png"), Path.of("754be574a5d44a28_d2021ea4827dd834.png"));
        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     FILES,
                     magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G')
                 )) {
            entries.forEach(entry -> fileNames.add(entry.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileNames).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Match all files (no first bytes specified)")
    void magicNumber_acceptEmptyBytes_allFiles() {
        List<Path> expected = List.of(
            Path.of("Logo_Team_Tinkoff.png"),
            Path.of("754be574a5d44a28_d2021ea4827dd834.png"),
            Path.of("anchor"),
            Path.of("chchh.jpeg"),
            Path.of("sadsadsa.png"),
            Path.of("asdfhjsahd.txt")
        );
        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     FILES,
                     magicNumber()
                 )) {
            entries.forEach(entry -> fileNames.add(entry.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileNames).containsExactlyInAnyOrderElementsOf(expected);
    }
}
