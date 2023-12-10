package edu.hw9.task1.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuantityTest {
    @Test
    @DisplayName("Getting data from an empty metric")
    void getData_whenNoData_thenNaN() {
        Metric quantity = new Quantity();
        MetricData data = quantity.getData();
        assertThat(data.metricName()).isEqualTo("quantity");
        assertThat(data.value()).isEqualTo(0);
    }

    @RepeatedTest(10)
    @DisplayName("Getting data from a metric")
    void getData_whenDataPassed_thenCorrect() {
        Metric quantity = new Quantity();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            double current = random.nextDouble() * random.nextLong();
            quantity.consume(current);
        }
        MetricData data = quantity.getData();
        assertThat(data.metricName()).isEqualTo("quantity");
        assertThat(data.value()).isEqualTo(1000);
    }
}
