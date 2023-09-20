package io.github.sng78.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;
    private final List<Organization> items;

    public OrganizationSection(List<Organization> items) {
        this.items = items;
    }

    public List<Organization> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        StringBuilder elements = new StringBuilder();
        for (Organization item : items) {
            elements.append("\n")
                    .append(item);
        }
        return elements.toString();
    }
}
