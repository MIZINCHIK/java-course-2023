package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

public abstract class AdjusterParser implements DateParser {
    private final String regex;

    protected AdjusterParser(String regex) {
        this.regex = regex;
    }

    public LocalDate parseDate(String date) {
        LocalDate result = LocalDate.now();
        return result.plus(getAdjustment(date));
    }

    public boolean canParseDate(String date) {
        return date.matches(regex);
    }

    abstract TemporalAmount getAdjustment(String date);
}
