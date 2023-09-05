package io.github.sng78.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final String name;
    private final String website;
    private final List<Period> periods;

    public Organization(String name, String website, Period period) {
        this.name = name;
        this.website = website;
        periods = new ArrayList<>();
        periods.add(period);
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(name)
                .append("\n")
                .append(website)
                .append("\n");
        for (Period period : periods) {
            output.append(period);
        }
        return output.toString();
    }
}
