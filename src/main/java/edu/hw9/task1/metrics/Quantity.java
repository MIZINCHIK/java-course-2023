package edu.hw9.task1.metrics;

public class Quantity implements Metric {
    private static final String METRIC_NAME = "quantity";
    double quantity = 0;

    @Override
    public MetricData getData() {
        return new MetricData(METRIC_NAME, quantity);
    }

    @Override
    public void consume(double data) {
        quantity++;
    }
}
