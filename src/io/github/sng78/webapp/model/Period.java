package io.github.sng78.webapp.model;

import java.time.LocalDate;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private final String description;

    public Period(LocalDate startDate, LocalDate endDate, String position, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return startDate.getMonthValue() + "/" + startDate.getYear() + " - "
                + endDate.getMonthValue() + "/" + endDate.getYear() + "\n" +
                (!position.isEmpty() ? position + "\n" : "") +
                description + "\n";
    }
}
