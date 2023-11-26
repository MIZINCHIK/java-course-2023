package edu.hw5.task3.parsers;

import java.time.format.DateTimeFormatter;

public class ReversedDateParser extends FormatDateParser {
    public ReversedDateParser() {
        super(DateTimeFormatter.ofPattern("M/d/[yyyy][yy]"));
    }
}
