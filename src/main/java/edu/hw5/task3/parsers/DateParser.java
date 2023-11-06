package edu.hw5.task3.parsers;

import java.time.LocalDate;

public interface DateParser {
    LocalDate parseDate(String date);

    boolean canParseDate(String date);
}
