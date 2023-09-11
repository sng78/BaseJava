package io.github.sng78.webapp.model;

import java.util.*;

public class Organization {
    private final String name;
    private final String website;
    private final List<Period> periods;

    public Organization(String name, String website, Period... period) {
        this.name = name;
        this.website = website;
        periods = new ArrayList<>();
        Collections.addAll(periods, period);
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name)
                && Objects.equals(website, that.website)
                && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
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
