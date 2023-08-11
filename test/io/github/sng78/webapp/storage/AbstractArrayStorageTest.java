package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void get() {

    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }
}
