package edu.hw5.task3.parsers;

import java.time.format.DateTimeFormatter;

public class StandardDateParser extends FormatDateParser {
    public StandardDateParser() {
        super(DateTimeFormatter.ofPattern("yyyy-MM-d"));
    }
}
