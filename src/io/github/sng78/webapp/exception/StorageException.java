package io.github.sng78.webapp.exception;

import java.sql.SQLException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message) {
        this(message, (String) null);
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
        uuid = null;
    }

    public StorageException(SQLException e) {
        this(e.getMessage(), e);
    }

    public String getUuid() {
        return uuid;
    }
}
