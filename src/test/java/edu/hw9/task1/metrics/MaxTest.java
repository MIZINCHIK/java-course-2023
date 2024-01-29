package edu.hw9.task1.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MaxTest {
    @Test
    @DisplayName("Getting data from an empty metric")
    void getData_whenNoData_thenNaN() {
        Metric max = new Max();
        MetricData data = max.getData();
        assertThat(data.metricName()).isEqualTo("max value");
        assertThat(data.value()).isNaN();
    }

    @RepeatedTest(10)
    @DisplayName("Getting data from a metric")
    void getData_whenDataPassed_thenCorrect() {
        Metric max = new Max();
        double maxValue = Double.MIN_VALUE;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            double current = random.nextDouble() * random.nextLong();
            maxValue = Math.max(maxValue, current);
            max.consume(current);
        }
        MetricData data = max.getData();
        assertThat(data.metricName()).isEqualTo("max value");
        assertThat(data.value()).isEqualTo(maxValue);
    }
}
