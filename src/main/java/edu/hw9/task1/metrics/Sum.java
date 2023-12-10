package edu.hw9.task1.metrics;

public class Sum implements Metric {
    private static final String METRIC_NAME = "summed data";
    private double sum;

    @Override
    public MetricData getData() {
        return new MetricData(METRIC_NAME, sum);
    }

    @Override
    public void consume(double data) {
        sum += data;
    }
}
