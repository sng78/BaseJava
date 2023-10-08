package io.github.sng78.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String website;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(String name, String website, Period... period) {
        this.name = name;
        this.website = website;
        periods = new ArrayList<>();
        Collections.addAll(periods, period);
    }

    public Organization(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;
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
