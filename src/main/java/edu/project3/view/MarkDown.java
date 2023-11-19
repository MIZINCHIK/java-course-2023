package edu.project3.view;

import edu.project3.reports.LogReport;
import java.util.List;
import static edu.project3.logs.HttpResponseCode.getMessage;
import static java.lang.System.lineSeparator;

public class MarkDown {
    public static final String GENERAL_HEADER = "## General information";
    public static final String GENERAL_HEADERS = "|Metric|Value(s)|";
    public static final String SOURCES = "Sources";
    public static final String STARTING_DATE = "Starting date";
    public static final String ENDING_DATE = "Ending date";
    public static final String REQUESTS_AMOUNT = "Amount of requests";
    public static final String AVERAGE_REQUEST_SIZE = "Average request size";
    public static final String RESOURCES_HEADER = "## Requested Resources";
    public static final String RESOURCES_HEADERS = "|Resource|Amount|";
    public static final String RESPONSE_HEADER = "## Response codes";
    public static final String RESPONSE_HEADERS = "|Code|Name|Amount|";
    public static final String DOUBLE_SEPARATOR = "|-|-|";
    public static final String TRIPLE_SEPARATOR = "|-|-|-|";

    private MarkDown() {
        throw new IllegalStateException();
    }

    public static String formatReport(LogReport report) {
        StringBuilder builder = new StringBuilder();
        builder.append(GENERAL_HEADER).append(lineSeparator())
            .append(GENERAL_HEADERS).append(lineSeparator()).append(DOUBLE_SEPARATOR).append(lineSeparator());
        List<String> sources = report.sources();
        String firstSource = sources.isEmpty() ? "" : sources.getFirst();
        builder.append("|" + SOURCES + "|").append(firstSource).append(lineSeparator());
        for (int i = 1; i < sources.size(); i++) {
            builder.append("| |").append(sources.get(i)).append(lineSeparator());
        }
        builder.append("|" + STARTING_DATE + "|").append(report.startDate().toString()).append("|")
            .append(lineSeparator());
        builder.append("|" + ENDING_DATE + "|").append(report.endDate().toString()).append("|").append(lineSeparator());
        builder.append("|" + REQUESTS_AMOUNT + "|").append(report.requestsAmount()).append("|").append(lineSeparator());
        builder.append("|" + AVERAGE_REQUEST_SIZE + "|").append(report.averageResponseSize()).append("|")
            .append(lineSeparator());
        builder.append(RESOURCES_HEADER).append(lineSeparator())
            .append(RESOURCES_HEADERS).append(lineSeparator()).append(DOUBLE_SEPARATOR).append(lineSeparator());
        for (var resource : report.mostFrequentResources()) {
            builder.append("|").append(resource.getKey()).append("|").append(resource.getValue())
                .append("|").append(lineSeparator());
        }
        builder.append(RESPONSE_HEADER).append(lineSeparator())
            .append(RESPONSE_HEADERS).append(lineSeparator()).append(TRIPLE_SEPARATOR).append(lineSeparator());
        for (var code : report.mostFrequentResponseCodes()) {
            builder.append("|").append(code.getKey()).append("|").append(getMessage(code.getKey()))
                .append("|").append(code.getValue()).append("|").append(lineSeparator());
        }
        return builder.toString();
    }
}
