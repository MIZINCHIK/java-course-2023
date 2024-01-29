package edu.hw9.task1.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AverageTest {
    @Test
    @DisplayName("Getting data from an empty metric")
    void getData_whenNoData_thenNaN() {
        Metric average = new Average();
        MetricData data = average.getData();
        assertThat(data.metricName()).isEqualTo("average");
        assertThat(data.value()).isNaN();
    }

    @RepeatedTest(10)
    @DisplayName("Getting data from a metric")
    void getData_whenDataPassed_thenCorrect() {
        Metric average = new Average();
        double total = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            double current = random.nextDouble() * random.nextLong();
            total += current;
            average.consume(current);
        }
        MetricData data = average.getData();
        assertThat(data.metricName()).isEqualTo("average");
        assertThat(data.value()).isEqualTo(total / 1000);
    }
}
