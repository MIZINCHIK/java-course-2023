package edu.project3.view;

import edu.project3.reports.LogReport;
import java.util.Collection;
import static edu.project3.logs.HttpResponseCode.getMessage;
import static java.lang.System.lineSeparator;

public class Adoc {
    public static final String TABLE_BORDER = "|=======================";
    public static final String OPTIONS = "[options=\"header\"]";
    public static final String GENERAL_HEADER = ".General information";
    public static final String GENERAL_COLUMNS = "|Metric|Value(s)";
    public static final String SOURCES = "Sources";
    public static final String STARTING_DATE = "Starting date";
    public static final String ENDING_DATE = "Ending date";
    public static final String REQUESTS_AMOUNT = "Amount of requests";
    public static final String AVERAGE_RESPONSE_SIZE = "Average response size";
    public static final String MAX_RESPONSE_SIZE = "Max response size";
    public static final String RESOURCES_HEADER = ".Requested Resources";
    public static final String RESOURCES_COLUMNS = "|Resource|Amount";
    public static final String RESPONSE_HEADER = ".Response codes";
    public static final String RESPONSE_COLUMNS = "|Code|Name|Amount";
    public static final String POPULAR_METHOD_HEADER = ".Most Popular method on Friday the 13th";
    public static final String POPULAR_METHOD_COLUMNS = "|Method Name|Amount";

    private Adoc() {
        throw new IllegalStateException();
    }

    public static String formatReport(LogReport report) {
        StringBuilder builder = new StringBuilder();
        appendTableTop(builder, GENERAL_HEADER, OPTIONS, GENERAL_COLUMNS);
        appendMultipleValueRow(builder, SOURCES, report.sources());
        appendSingleValueDoubleRow(builder, STARTING_DATE, report.startDate().toString());
        appendSingleValueDoubleRow(builder, ENDING_DATE, report.endDate().toString());
        appendSingleValueDoubleRow(builder, REQUESTS_AMOUNT, String.valueOf(report.requestsAmount()));
        appendSingleValueDoubleRow(builder, AVERAGE_RESPONSE_SIZE, String.valueOf(report.averageResponseSize()));
        appendSingleValueDoubleRow(builder, MAX_RESPONSE_SIZE, String.valueOf(report.maxResponseSize()));
        appendTableBottom(builder);
        appendTableTop(builder, RESOURCES_HEADER, OPTIONS, RESOURCES_COLUMNS);
        for (var resource : report.mostFrequentResources()) {
            appendSingleValueDoubleRow(builder, resource.getKey(), String.valueOf(resource.getValue()));
        }
        appendTableBottom(builder);
        appendTableTop(builder, RESPONSE_HEADER, OPTIONS, RESPONSE_COLUMNS);
        for (var code : report.mostFrequentResponseCodes()) {
            appendSingleValueMultipleRow(builder, String.valueOf(code.getKey()), getMessage(code.getKey()),
                String.valueOf(code.getValue())
            );
        }
        appendTableBottom(builder);
        appendTableTop(builder, POPULAR_METHOD_HEADER, OPTIONS, POPULAR_METHOD_COLUMNS);
        var method = report.mostFrequentMethodOnFridayThe13Th();
        appendSingleValueDoubleRow(builder, method.getKey(), String.valueOf(method.getValue()));
        appendTableBottom(builder);
        return builder.toString();
    }

    public static StringBuilder appendTableTop(
        StringBuilder builder,
        String header,
        String options,
        String columnNames
    ) {
        return builder.append(header).append(lineSeparator())
            .append(options).append(lineSeparator())
            .append(TABLE_BORDER).append(lineSeparator())
            .append(columnNames).append(lineSeparator());
    }

    public static StringBuilder appendTableBottom(
        StringBuilder builder
    ) {
        return builder.append(TABLE_BORDER).append(lineSeparator());
    }

    public static StringBuilder appendSingleValueDoubleRow(StringBuilder builder, String rowName, String value) {
        return builder.append("|").append(rowName).append("|").append(value).append(lineSeparator());
    }

    public static StringBuilder appendSingleValueMultipleRow(StringBuilder builder, String rowName, String... values) {
        builder.append("|").append(rowName);
        for (String value : values) {
            builder.append("|").append(value);
        }
        return builder.append(lineSeparator());
    }

    public static StringBuilder appendMultipleValueRow(
        StringBuilder builder,
        String rowName,
        Collection<String> values
    ) {
        builder.append(".").append(values.size()).append("+^.^|").append(rowName);
        for (String value : values) {
            builder.append("|").append(value).append(lineSeparator());
        }
        return builder;
    }
}
