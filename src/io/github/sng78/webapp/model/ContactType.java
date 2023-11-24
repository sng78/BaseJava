package io.github.sng78.webapp.model;

public enum ContactType {
    PHONE("Моб.") {
        @Override
        public String toHtml(String value) {
            return getTitle() + ": " + value;
        }
    },
    TELEGRAM("Telegram") {
        @Override
        public String toHtml(String value) {
            return getTitle() + ": " + "<a href='https://" + value + "' target='_blank'>" + value + "</a>";
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml(String value) {
            return getTitle() + ": " + "<a href='skype:" + value + "?chat'>" + value + "</a>";
        }
    },
    EMAIL("Email") {
        @Override
        public String toHtml(String value) {
            return getTitle() + ": " + "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stack Overflow"),
    WEBSITE("Домашняя страница") {
        @Override
        public String toHtml(String value) {
            return "<a href='" + value + "' target='_blank'>" + getTitle() + "</a>";
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" :
                "<a href='" + value + "' target='_blank'>" + "Профиль " + getTitle() + "</a>";
    }
}
