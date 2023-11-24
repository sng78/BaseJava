package io.github.sng78.webapp.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String format(LocalDate date) {
        return date == null ? "" : date.format(DTF);
    }

    public static LocalDate parse(String date) {
        YearMonth yearMonth = YearMonth.parse(date, DTF);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
