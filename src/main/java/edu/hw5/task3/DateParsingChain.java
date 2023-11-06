package edu.hw5.task3;

import edu.hw5.task3.parsers.DateParser;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DateParsingChain {
    private final List<DateParser> parsers;

    public DateParsingChain() {
        this.parsers = new ArrayList<>();
    }

    public void addParserToChain(DateParser parser) {
        if (parser != null) {
            parsers.add(parser);
        }
    }

    public Optional<LocalDate> parseDate(String date) {
        return parsers.stream()
                .filter(parser -> parser.canParseDate(date))
                .findFirst().map(parser -> parser.parseDate(date));
    }
}
