package edu.hw5.task3.parsers;

import java.time.Period;
import java.time.temporal.TemporalAmount;

public class DaysAgoAdjusterParser extends AdjusterParser {
    public DaysAgoAdjusterParser() {
        super("^(1 day ago|([2-9]|[1-9]\\d+) days ago)$");
    }

    @Override
    TemporalAmount getAdjustment(String date) {
        return Period.ofDays(-Integer.parseInt(date.replaceAll("\\D", "")));
    }
}
