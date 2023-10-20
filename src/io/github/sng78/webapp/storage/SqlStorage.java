package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;
import io.github.sng78.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName)
                    .thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume")) {

            ps.execute();

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(
                             "UPDATE resume r SET full_name = ? WHERE uuid = ?")) {

            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {

            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {

            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {

            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> resumeList = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("SELECT * FROM resume")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String fullName = rs.getString("full_name");
                resumeList.add(new Resume(uuid, fullName));
            }
            resumeList.sort(RESUME_COMPARATOR);
            return resumeList;

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        LOG.info("Size");
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("SELECT count(*) FROM resume")) {

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
