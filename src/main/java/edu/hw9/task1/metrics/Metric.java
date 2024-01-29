package edu.hw9.task1.metrics;

import java.util.Collection;

public interface Metric {
    MetricData getData();

    void consume(double data);

    default void consume(Collection<Double> data) {
        for (double dataValue : data) {
            consume(dataValue);
        }
    }
}
