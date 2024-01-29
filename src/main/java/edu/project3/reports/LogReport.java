package edu.project3.reports;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record LogReport(List<String> sources, OffsetDateTime startDate, OffsetDateTime endDate, long requestsAmount,
                        long averageResponseSize, double maxResponseSize,
                        List<Map.Entry<String, Integer>> mostFrequentResources,
                        List<Map.Entry<Integer, Integer>> mostFrequentResponseCodes,
                        Map.Entry<String, Integer> mostFrequentMethodOnFridayThe13Th) {
}
