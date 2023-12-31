package io.github.sng78.webapp;

import io.github.sng78.webapp.storage.SqlStorage;
import io.github.sng78.webapp.storage.Storage;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES =
            new File("C:/Users/SnG78/IdeaProjects/BaseJava/config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;
    private final String protectedUuid;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SqlStorage(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
            protectedUuid = properties.getProperty("protected.uuid");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    public String getProtectedUuid() {
        return protectedUuid;
    }
}
