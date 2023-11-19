package edu.project3.reports;

import edu.project3.logs.Log;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogReportBuilder {
    public static final int TOP_SIZE = 3;
    private final List<String> sources;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final Map<String, Integer> resourcesFrequencies;
    private final Map<Integer, Integer> responseCodeFrequencies;
    private long requestsAmount;
    private double totalResponseSize;

    public LogReportBuilder(
        List<String> sources, OffsetDateTime startDate, OffsetDateTime endDate
    ) {
        this.sources = sources;
        this.startDate = startDate;
        this.endDate = endDate;
        requestsAmount = 0;
        totalResponseSize = 0;
        resourcesFrequencies = new HashMap<>();
        responseCodeFrequencies = new HashMap<>();
    }

    public void considerLog(Log log) {
        resourcesFrequencies.put(log.resource(), resourcesFrequencies.getOrDefault(log.resource(), 0) + 1);
        responseCodeFrequencies.put(log.code(), responseCodeFrequencies.getOrDefault(log.code(), 0) + 1);
        requestsAmount++;
        totalResponseSize += log.bytesSent();
    }

    public LogReport build() {
        return new LogReport(
            sources,
            startDate,
            endDate,
            requestsAmount,
            (long) (totalResponseSize / requestsAmount),
            resourcesFrequencies.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(TOP_SIZE)
                .collect(Collectors.toList()),
            responseCodeFrequencies.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(TOP_SIZE)
                .collect(Collectors.toList())
        );
    }
}

