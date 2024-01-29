package edu.project3.logs;

import java.net.UnknownHostException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project3.logs.Log.parseLog;
import static java.net.InetAddress.getByName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogTest {
    @Test
    @DisplayName("Incorrect logs parse into optional empty")
    void parseLog_incorrectLog_optionalEmpty() {
        assertThat(parseLog("asdsadasdasd")).isEmpty();
    }

    @Test
    @DisplayName("Logs with incorrect ips parse into optional empty")
    void parseLog_incorrectIp_optionalEmpty() {
        assertThat(parseLog(
            "93 .180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "93.9999.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"" +
                "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "2001:0000:130Z:0000:0000:09C0:876A:130B - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 H" +
                "TTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "2001:0000 :130F:0000:0000:09C0:876A:130B - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 H" +
                "TTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "2001:11111:130F:0000:0000:09C0:876A:130B - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" " +
                "304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
    }

    @Test
    @DisplayName("Logs parse correct ips accordingly")
    void parseLog_correctIp_correctOutput() throws UnknownHostException {
        Optional<Log> log1 = parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0" +
                " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"");
        Optional<Log> log2 = parseLog(
            "2001:1111:130F:0000:0000:09C0:876A:130B - - [17/May/2015:08:05:32 +0000] \"GET " +
                "/downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"");
        assertThat(log1.isPresent()).isTrue();
        assertThat(log2.isPresent()).isTrue();
        assertThat(log1.get().remoteAddress()).isEqualTo(getByName("93.180.71.3"));
        assertThat(log2.get().remoteAddress()).isEqualTo(getByName("2001:1111:130F:0000:0000:09C0:876A:130B"));
    }

    @Test
    @DisplayName("Incorrect time results in empty optional")
    void parseLog_incorrectTime_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +18:30] \"Ddsfsdf /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "93.180.71.3 - - [17/10/2015:08:05:32 +0000] \"Ddsfsdf /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog("93.180.71.3 - - [17/May/2015:08:05:32] \"Ddsfsdf /downloads/product_1 HTTP/1.1\" 304 0 " +
            "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
    }

    @Test
    @DisplayName("Logs parse time correctly")
    void parseLog_correctTime_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 -0700] \"GET /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().timeLocal()).isEqualTo(OffsetDateTime.of(2015, 5, 17,
            8, 5, 32, 0, ZoneOffset.of("-0700")
        ));
    }

    @Test
    @DisplayName("Incorrect request results in empty optional")
    void parseLog_incorrectRequest_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"Ddsfsdf /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
    }

    @Test
    @DisplayName("Logs parse request method correctly")
    void parseLog_correctRequest_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().requestMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("Incorrect request status results in empty optional")
    void parseLog_incorrectStatus_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 3043 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog("93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 30 0 " +
            "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" -300 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
    }

    @Test
    @DisplayName("Logs parse request status correctly")
    void parseLog_correctStatus_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().code()).isEqualTo(304);
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().code()).isEqualTo(404);
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 420 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().code()).isEqualTo(420);
    }

    @Test
    @DisplayName("Incorrect bytes sent results in empty optional")
    void parseLog_incorrectBytes_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 -1 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 asfdsadasd " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")).isEmpty();
    }

    @Test
    @DisplayName("Logs parse bytes sent correctly")
    void parseLog_correctBytes_optionalEmpty() {
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().bytesSent()).isEqualTo(0);
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 100 " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().bytesSent()).isEqualTo(100);
        assertThat(parseLog(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 420 " + Long.MAX_VALUE +
                " " +
                "\"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"")
            .get().bytesSent()).isEqualTo(Long.MAX_VALUE);
    }
}
