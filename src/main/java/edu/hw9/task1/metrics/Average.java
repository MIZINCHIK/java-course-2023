package edu.hw9.task1.metrics;

public class Average implements Metric {
    private static final String METRIC_NAME = "average";
    private double sum;
    private double quantity;

    @Override
    public MetricData getData() {
        return new MetricData(METRIC_NAME, sum / quantity);
    }

    @Override
    public void consume(double data) {
        quantity++;
        sum += data;
    }
}
