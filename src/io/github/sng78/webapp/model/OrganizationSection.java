package io.github.sng78.webapp.model;

import java.util.List;

public class OrganizationSection extends Section {
    private final List<Organization> items;

    public OrganizationSection(List<Organization> items) {
        this.items = items;
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
