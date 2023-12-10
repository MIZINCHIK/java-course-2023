package edu.hw9.task1.metrics;

public class Min implements Metric {
    private static final String METRIC_NAME = "min value";
    private double minValue = Double.NaN;

    @Override
    public MetricData getData() {
        return new MetricData(METRIC_NAME, minValue);
    }

    @Override
    public void consume(double data) {
        if (Double.isNaN(minValue)) {
            minValue = data;
        } else {
            minValue = Math.min(data, minValue);
        }
    }
}
