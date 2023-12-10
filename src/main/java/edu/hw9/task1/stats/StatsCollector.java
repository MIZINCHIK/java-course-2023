package edu.hw9.task1.stats;

import edu.hw9.task1.metrics.MetricData;
import java.util.List;

public interface StatsCollector {
    void push(double data);

    default void push(double[] data) {
        for (double dataValue : data) {
            push(dataValue);
        }
    }

    List<MetricData> getStats();
}
