package edu.project3.reports;

import edu.project3.logs.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogReportBuilderTest {
    LogReportBuilder builder = new LogReportBuilder(List.of(), OffsetDateTime.of(
        2012, 1, 1,
        1, 1, 1, 0, ZoneOffset.of("+0000")
    ), OffsetDateTime.of(
        2015, 1, 1,
        1, 1, 1, 0, ZoneOffset.of("+0000")
    ));

    @BeforeEach
    void fillBuilder() throws UnknownHostException {
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.3"), "user1",
            OffsetDateTime.of(
                2015, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource1", null, 300, 101, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.12"), "user1",
            OffsetDateTime.of(
                2000, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource1", null, 300, 200, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.3"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource2", null, 404, 108, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.12"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource3", null, 404, 101, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.3"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource2", null, 300, 0, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.12"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource5", null, 401, 0, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.3"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource3", null, 404, 1, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.5"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource3", null, 402, 1000000, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.6"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource2", null, 401, 200, null, null
        ));
        builder.considerLog(new Log(InetAddress.getByName("93.180.71.6"), "user1",
            OffsetDateTime.of(
                2014, 2, 1,
                1, 1, 1, 0, ZoneOffset.of("+0000")
            ), "GET",
            "/resource3", null, 300, 10, null, null
        ));

    }

    @Test
    @DisplayName("LogReportBuilder collecting data")
    void build_variousLogs_correctData() {
        var report = builder.build();
        assertThat(report.averageResponseSize()).isEqualTo(100_072);
        assertThat(report.requestsAmount()).isEqualTo(10);
        assertThat(report.requestsAmount()).isEqualTo(10);
        var topResources = report.mostFrequentResources();
        assertThat(topResources.size()).isEqualTo(3);
        assertThat(topResources.get(0).getKey()).isEqualTo("/resource3");
        assertThat(topResources.get(1).getKey()).isEqualTo("/resource2");
        assertThat(topResources.get(2).getKey()).isEqualTo("/resource1");
        assertThat(topResources.get(0).getValue()).isEqualTo(4);
        assertThat(topResources.get(1).getValue()).isEqualTo(3);
        assertThat(topResources.get(2).getValue()).isEqualTo(2);
        var topCodes = report.mostFrequentResponseCodes();
        assertThat(topCodes.size()).isEqualTo(3);
        assertThat(topCodes.get(0).getKey()).isEqualTo(300);
        assertThat(topCodes.get(1).getKey()).isEqualTo(404);
        assertThat(topCodes.get(2).getKey()).isEqualTo(401);
        assertThat(topResources.get(0).getValue()).isEqualTo(4);
        assertThat(topResources.get(1).getValue()).isEqualTo(3);
        assertThat(topResources.get(2).getValue()).isEqualTo(2);
    }
}
