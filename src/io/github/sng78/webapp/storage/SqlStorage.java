package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.model.ContactType;
import io.github.sng78.webapp.model.Resume;
import io.github.sng78.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM resume")) {
                ps.execute();
            }
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume\n" +
                        "   SET full_name = ?\n" +
                        " WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE contact\n" +
                        "   SET value = ?\n" +
                        " WHERE type = ?")) {
                for (Map.Entry<ContactType, String> contactsMap : r.getContacts().entrySet()) {
                    ps.setString(1, contactsMap.getValue());
                    ps.setString(2, contactsMap.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name)\n" +
                        "VALUES (?, ?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value)\n" +
                        "VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> contactsMap : r.getContacts().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, contactsMap.getKey().name());
                    ps.setString(3, contactsMap.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute(
                "   SELECT *\n" +
                    "     FROM resume r\n" +
                    "LEFT JOIN contact c\n" +
                    "       ON r.uuid = c.resume_uuid\n" +
                    "    WHERE uuid = ?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM resume\n" +
                        " WHERE uuid = ?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        return sqlHelper.execute(
                "   SELECT *\n" +
                    "     FROM resume r\n" +
                    "LEFT JOIN contact c\n" +
                    "       ON r.uuid = c.resume_uuid\n" +
                    " ORDER BY full_name, uuid", ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumesMap = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume r = resumesMap.get(uuid);
                        if (r == null) {
                            r = new Resume(uuid, rs.getString("full_name"));
                            resumesMap.put(uuid, r);
                        }
                        addContact(rs, r);
                    }
                    return new ArrayList<>(resumesMap.values());
                });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.execute(
                "SELECT count(*)\n" +
                    "  FROM resume", ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    public void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        ContactType contactType = ContactType.valueOf(rs.getString("type"));
        r.setContact(contactType, value);
    }
}
