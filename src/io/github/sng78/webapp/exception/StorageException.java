package io.github.sng78.webapp.exception;

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

    public String getUuid() {
        return uuid;
    }
}
