package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class Task2 {
    private Task2() {
        throw new IllegalStateException();
    }

    @SuppressWarnings("MagicNumber")
    public static List<LocalDate> getAllFridaysThe13th(int year) {
        LocalDate start = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(year + 1, Month.JANUARY, 1);
        return start.datesUntil(end)
            .filter(date -> date.getDayOfWeek() == DayOfWeek.FRIDAY
                && date.getDayOfMonth() == 13)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("MagicNumber")
    public static LocalDate nextFridayThe13th(LocalDate currentDate) {
        TemporalAdjuster adjuster = TemporalAdjusters.next(DayOfWeek.FRIDAY);
        LocalDate resultDate = currentDate.with(adjuster);
        while (resultDate.getDayOfMonth() != 13) {
            resultDate = resultDate.with(adjuster);
        }
        return resultDate;
    }
}
