package io.github.sng78.webapp.sql;

import io.github.sng78.webapp.exception.ExistStorageException;
import io.github.sng78.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
