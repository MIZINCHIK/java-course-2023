package edu.hw9.task1.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SumTest {
    @Test
    @DisplayName("Getting data from an empty metric")
    void getData_whenNoData_thenNaN() {
        Metric sum = new Sum();
        MetricData data = sum.getData();
        assertThat(data.metricName()).isEqualTo("summed data");
        assertThat(data.value()).isEqualTo(0);
    }

    @RepeatedTest(10)
    @DisplayName("Getting data from a metric")
    void getData_whenDataPassed_thenCorrect() {
        Metric sum = new Sum();
        double total = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            double current = random.nextDouble() * random.nextLong();
            total += current;
            sum.consume(current);
        }
        MetricData data = sum.getData();
        assertThat(data.metricName()).isEqualTo("summed data");
        assertThat(data.value()).isEqualTo(total);
    }
}
