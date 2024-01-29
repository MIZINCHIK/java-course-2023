package edu.hw9.task1.stats;

import edu.hw9.task1.metrics.MetricData;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConcurrentStatsCollectorTest {
    @RepeatedTest(10)
    @DisplayName("Getting data in a single thread")
    void getStats_whenSingleThread_thenCorrect() {
        ConcurrentStatsCollector collector = new ConcurrentStatsCollector();
        double sum = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            double current = random.nextDouble() * random.nextLong();
            sum += current;
            max = Math.max(current, max);
            min = Math.min(current, min);
            collector.push(current);
        }
        var stats = collector.getStats();
        assertThat(stats.size()).isEqualTo(5);
        assertThat(stats.contains(new MetricData("max value", max))).isTrue();
        assertThat(stats.contains(new MetricData("min value", min))).isTrue();
        assertThat(stats.contains(new MetricData("average", sum / 1000))).isTrue();
        assertThat(stats.contains(new MetricData("quantity", 1000))).isTrue();
        assertThat(stats.contains(new MetricData("summed data", sum))).isTrue();
    }

    @RepeatedTest(10)
    @DisplayName("Getting data in multiple threads")
    void getStats_whenMultiThread_thenCorrect() {
        try (var pool = Executors.newFixedThreadPool(15)) {
            ConcurrentStatsCollector collector = new ConcurrentStatsCollector();
            for (int i = 0; i < 15; i++) {
                pool.submit(() -> {
                    var random = ThreadLocalRandom.current();
                    for (int j = 0; j < 1000; j++) {
                        collector.push(random.nextDouble() * random.nextLong());
                    }
                });
            }
            for (int i = 0; i < 1000; i++) {
                var metrics = collector.getStats();
                assertThat(metrics.size()).isEqualTo(5);
                MetricData average = null;
                MetricData sum = null;
                MetricData quantity = null;
                for (var metric : metrics) {
                    switch (metric.metricName()) {
                        case "average":
                            average = metric;
                            break;
                        case "summed data":
                            sum = metric;
                            break;
                        case "quantity":
                            quantity = metric;
                            break;
                    }
                }
                assertThat(average).isNotNull();
                assertThat(sum).isNotNull();
                assertThat(quantity).isNotNull();
                assertThat(sum.value() / quantity.value()).isEqualTo(average.value());
            }
        }
    }
}
