package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatDateParser implements DateParser {
    private final DateTimeFormatter dateFormatter;

    protected FormatDateParser(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }

    @Override
    public boolean canParseDate(String date) {
        try {
            dateFormatter.parse(date);
        } catch (DateTimeParseException exception) {
            return false;
        }
        return true;
    }
}
