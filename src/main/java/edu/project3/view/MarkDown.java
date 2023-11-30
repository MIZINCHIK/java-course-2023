package edu.project3.view;

import edu.project3.reports.LogReport;
import java.util.List;
import static edu.project3.logs.HttpResponseCode.getMessage;
import static java.lang.System.lineSeparator;

public class MarkDown {
    public static final String GENERAL_HEADER = "## General information";
    public static final String GENERAL_COLUMNS = "|Metric|Value(s)|";
    public static final String SOURCES = "Sources";
    public static final String STARTING_DATE = "Starting date";
    public static final String ENDING_DATE = "Ending date";
    public static final String REQUESTS_AMOUNT = "Amount of requests";
    public static final String AVERAGE_RESPONSE_SIZE = "Average response size";
    public static final String MAX_RESPONSE_SIZE = "Max response size";
    public static final String RESOURCES_HEADER = "## Requested Resources";
    public static final String RESOURCES_COLUMNS = "|Resource|Amount|";
    public static final String RESPONSE_HEADER = "## Response codes";
    public static final String RESPONSE_COLUMNS = "|Code|Name|Amount|";
    public static final String POPULAR_METHOD_HEADER = "## Most Popular method on Friday the 13th";
    public static final String POPULAR_METHOD_COLUMNS = "|Method Name|Amount|";

    private MarkDown() {
        throw new IllegalStateException();
    }

    @SuppressWarnings("MagicNumber")
    public static String formatReport(LogReport report) {
        StringBuilder builder = new StringBuilder();
        appendTableTop(builder, GENERAL_HEADER, GENERAL_COLUMNS, 2);
        appendMultipleValueRow(builder, SOURCES, report.sources());
        appendSingleValueDoubleRow(builder, STARTING_DATE, report.startDate().toString());
        appendSingleValueDoubleRow(builder, ENDING_DATE, report.endDate().toString());
        appendSingleValueDoubleRow(builder, REQUESTS_AMOUNT, String.valueOf(report.requestsAmount()));
        appendSingleValueDoubleRow(builder, AVERAGE_RESPONSE_SIZE, String.valueOf(report.averageResponseSize()));
        appendSingleValueDoubleRow(builder, MAX_RESPONSE_SIZE, String.valueOf(report.maxResponseSize()));
        appendTableTop(builder, RESOURCES_HEADER, RESOURCES_COLUMNS, 2);
        for (var resource : report.mostFrequentResources()) {
            appendSingleValueDoubleRow(builder, resource.getKey(), String.valueOf(resource.getValue()));
        }
        appendTableTop(builder, RESPONSE_HEADER, RESPONSE_COLUMNS, 3);
        for (var code : report.mostFrequentResponseCodes()) {
            appendSingleValueMultipleRow(
                builder,
                String.valueOf(code.getKey()),
                getMessage(code.getKey()),
                String.valueOf(code.getValue())
            );
        }
        appendTableTop(builder, POPULAR_METHOD_HEADER, POPULAR_METHOD_COLUMNS, 2);
        var method = report.mostFrequentMethodOnFridayThe13Th();
        appendSingleValueDoubleRow(builder, method.getKey(), String.valueOf(method.getValue()));
        return builder.toString();
    }

    public static StringBuilder appendTableTop(
        StringBuilder builder,
        String header,
        String columnNames,
        int columnsAmount
    ) {
        builder.append(header).append(lineSeparator())
            .append(columnNames).append(lineSeparator());
        builder.append("|-".repeat(Math.max(0, columnsAmount)));
        return builder.append("|").append(lineSeparator());
    }

    public static StringBuilder appendSingleValueDoubleRow(StringBuilder builder, String rowName, String value) {
        return builder.append("|").append(rowName).append("|").append(value).append("|").append(lineSeparator());
    }

    public static StringBuilder appendSingleValueMultipleRow(StringBuilder builder, String rowName, String... values) {
        builder.append("|").append(rowName).append("|");
        for (String value : values) {
            builder.append(value).append("|");
        }
        return builder.append(lineSeparator());
    }

    public static StringBuilder appendMultipleValueRow(StringBuilder builder, String rowName, List<String> values) {
        String firstValue = values.isEmpty() ? "" : values.getFirst();
        builder.append("|").append(rowName).append("|").append(firstValue).append(lineSeparator());
        for (int i = 1; i < values.size(); i++) {
            builder.append("| |").append(values.get(i)).append(lineSeparator());
        }
        return builder;
    }
}
