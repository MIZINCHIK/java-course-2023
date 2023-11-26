package edu.hw5.task3.parsers;

import java.time.Period;
import java.time.temporal.TemporalAmount;

public class NaturalAdjusterParser extends AdjusterParser {
    private static final String YESTERDAY = "yesterday";
    private static final String TODAY = "today";
    private static final String TOMORROW = "tomorrow";

    public NaturalAdjusterParser() {
        super("^(yesterday|today|tomorrow)$");
    }

    @Override
    TemporalAmount getAdjustment(String date) {
        return switch (date) {
            case YESTERDAY -> Period.ofDays(-1);
            case TODAY -> Period.ofDays(0);
            case TOMORROW -> Period.ofDays(1);
            default -> throw new IllegalStateException("Unexpected date format: " + date);
        };
    }
}
