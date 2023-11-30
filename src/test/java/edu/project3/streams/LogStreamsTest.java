package edu.project3.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project3.streams.LogStreams.createLogReport;
import static java.lang.System.lineSeparator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogStreamsTest {
    private static final Path FILES = Path.of(System.getProperty("user.dir"))
        .resolve("src/test/resources/edu/project3/");
    private static final Path FILE1 = FILES.resolve("test1.txt");
    private static final Path FILE2 = FILES.resolve("test2.txt");
    private static final Path FILE3 = FILES.resolve("test3.txt");

    @Test
    @DisplayName("Creating LogReport from paths to resources")
    void createLogReport_createFileWithLogs_correctReport() throws IOException {
        Files.createDirectories(FILES);
        var file1 = Files.createFile(FILES.resolve(FILE1));
        var file2 = Files.createFile(FILES.resolve(FILE2));
        var file3 = Files.createFile(FILES.resolve(FILE3));
        Files.writeString(
            file1,
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 11" +
                " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"" + lineSeparator()
                + "93.180.71.3 - - [17/May/2015:08:05:32 +18:30] \"Ddsfsdf /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"" + lineSeparator()
                + "93.180.71.3 - - [17/May/2011:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 11" +
                " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\""
        );
        Files.writeString(
            file2,
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 53" +
                " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"" + lineSeparator()
                + "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 110 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"" + lineSeparator()
        );
        Files.writeString(
            file3,
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0" +
                " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"" + lineSeparator()
                + "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"Ddsfsdf /downloads/product_1 HTTP/1.1\" 304 95 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"" + lineSeparator()
                + "93.180.71.3 - - [17/May/2018:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0" +
                " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\""
        );
        var report = createLogReport(
            new String[] {file1.toString(), file2.toString(), file3.toString()},
            OffsetDateTime.of(2012, 1, 1, 1, 1,
                1, 0, ZoneOffset.of("+0000")
            ),
            OffsetDateTime.of(2016, 1, 1, 1, 1,
                1, 0, ZoneOffset.of("+0000")
            )
        );
        Files.delete(file1);
        Files.delete(file2);
        Files.delete(file3);
        assertThat(report.requestsAmount()).isEqualTo(4);
        assertThat(report.averageResponseSize()).isEqualTo(43);
        assertThat(report.mostFrequentResponseCodes().size()).isEqualTo(1);
        assertThat(report.mostFrequentResources().size()).isEqualTo(1);
        assertThat(report.mostFrequentResponseCodes().getFirst().getKey()).isEqualTo(304);
        assertThat(report.mostFrequentResponseCodes().getFirst().getValue()).isEqualTo(4);
        assertThat(report.mostFrequentResources().getFirst().getKey()).isEqualTo("/downloads/product_1");
        assertThat(report.mostFrequentResources().getFirst().getValue()).isEqualTo(4);
    }
}
