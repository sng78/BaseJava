package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    @DisplayName("Тест метода clear")
    public void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    @DisplayName("Тест метода update")
    public void update() {
        storage.update(RESUME_2);
        Assertions.assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test
    @DisplayName("Тест метода save")
    public void save() {
        storage.save(new Resume("uuid4"));
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    @DisplayName("Тест метода get")
    public void get() {
        Assertions.assertEquals(RESUME_1, storage.get(UUID_1));
        Assertions.assertEquals(RESUME_2, storage.get(UUID_2));
        Assertions.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test
    @DisplayName("Тест на отсутствие резюме")
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    @DisplayName("Тест метода delete")
    public void delete() {
        storage.delete(UUID_2);
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    @DisplayName("Тест метода getAll")
    public void getAll() {
        Resume[] resumes = storage.getAll();
        Assertions.assertEquals(UUID_1, resumes[0].toString());
        Assertions.assertEquals(UUID_2, resumes[1].toString());
        Assertions.assertNotEquals("dummy", resumes[2].toString());
    }

    @Test
    @DisplayName("Тест метода size")
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    @DisplayName("Тест на переполнение хранилища")
    public void overflow() {
        storage.clear();
        try{
            for (int i = 0; i < AbstractArrayStorage.STORAGE_CAPACITY; i++) {
                storage.save(new Resume("" + i));
            }
        } catch (Exception e) {
            Assertions.fail("storage overflow occurred ahead of time - " + e);
        }

        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }
}
