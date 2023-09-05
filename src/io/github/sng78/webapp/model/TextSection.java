package io.github.sng78.webapp.model;

public class TextSection extends Section {
    private final String value;

    public TextSection(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\n" + value + "\n";
    }
}
