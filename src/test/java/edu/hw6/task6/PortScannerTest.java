package edu.hw6.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task6.PortScanner.scanPorts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PortScannerTest {
    @Test
    @DisplayName("Scanning not available ports")
    void scanPorts_default_correct() {
        String result = scanPorts();
        assertThat(result.contains("TCP             0")).isTrue();
        assertThat(result.contains("UDP             0")).isTrue();
    }
}
