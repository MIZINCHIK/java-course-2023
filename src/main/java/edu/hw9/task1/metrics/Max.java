package edu.hw9.task1.metrics;

public class Max implements Metric {
    private static final String METRIC_NAME = "max value";

    private double maxValue = Double.NaN;

    @Override
    public MetricData getData() {
        return new MetricData(METRIC_NAME, maxValue);
    }

    @Override
    public void consume(double data) {
        if (Double.isNaN(maxValue)) {
            maxValue = data;
        } else {
            maxValue = Math.max(data, maxValue);
        }
    }
}
