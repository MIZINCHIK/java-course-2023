package edu.hw9.task1.stats;

import edu.hw9.task1.metrics.Average;
import edu.hw9.task1.metrics.Max;
import edu.hw9.task1.metrics.Metric;
import edu.hw9.task1.metrics.MetricData;
import edu.hw9.task1.metrics.Min;
import edu.hw9.task1.metrics.Quantity;
import edu.hw9.task1.metrics.Sum;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentStatsCollector implements StatsCollector {
    private final BlockingQueue<Double> queue;
    private final Lock statsLock = new ReentrantLock(true);
    private final List<Metric> metrics;


    public ConcurrentStatsCollector() {
        queue = new LinkedBlockingQueue<>();
        metrics = List.of(new Average(), new Max(), new Min(), new Sum(), new Quantity());
    }

    @Override
    public void push(double data) {
        queue.add(data);
    }

    @Override
    public List<MetricData> getStats() {
        Queue<Double> auxiliary = new ArrayDeque<>();
        queue.drainTo(auxiliary);
        statsLock.lock();
        consume(auxiliary);
        List<MetricData> stats = metrics.stream()
            .map(Metric::getData)
            .toList();
        statsLock.unlock();
        return stats;
    }

    private void consume(Collection<Double> data) {
        for (Metric metric : metrics) {
            metric.consume(data);
        }
    }
}
