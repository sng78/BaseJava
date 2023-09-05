package io.github.sng78.webapp.model;

import java.util.List;

public class ListSection extends Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder elements = new StringBuilder();
        elements.append("\n");
        for (String item : items) {
            elements.append("• ")
                    .append(item)
                    .append("\n");
        }
        return elements.toString();
    }
}
