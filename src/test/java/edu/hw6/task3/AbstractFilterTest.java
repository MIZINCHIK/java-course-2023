package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractFilterTest {
    private static final String RESOURCES_FROM_ROOT = "src/test/resources/edu/hw6/task3";
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve(RESOURCES_FROM_ROOT);
    private static final AbstractFilter regularFile = Files::isRegularFile;
    private static final AbstractFilter filterNames1 = new AbstractFilter() {
        @Override
        public boolean accept(Path entry) throws IOException {
            String name = String.valueOf(entry.getFileName());
            return name.equals("Logo_Team_Tinkoff.png") || name.equals("chchh.jpeg") || name.equals("anchor");
        }
    };
    private static final AbstractFilter filterNames2 = new AbstractFilter() {
        @Override
        public boolean accept(Path entry) throws IOException {
            String name = String.valueOf(entry.getFileName());
            return name.equals("754be574a5d44a28_d2021ea4827dd834.png") || name.equals("sadsadsa.png") ||
                name.equals("anchor");
        }
    };

    @Test
    @DisplayName("Forming a chain of filters is equivalent to applying filters consecutively")
    void and_threeFilters_sameAsWithoutAChain() {
        List<Path> expected = List.of(Path.of("anchor"));
        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(
            FILES,
            regularFile.and(filterNames1).and(filterNames2)
        )) {
            entries.forEach(entry -> fileNames.add(entry.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileNames).containsExactlyInAnyOrderElementsOf(expected);
    }
}
