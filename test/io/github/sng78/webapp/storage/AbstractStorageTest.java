package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.Config;
import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.model.ContactType;
import io.github.sng78.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.github.sng78.webapp.ResumeTestData.createResume;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    private static final String UUID_1 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = createResume(UUID_1, "name1");
    private static final String UUID_2 = UUID.randomUUID().toString();
    public static final Resume RESUME_2 = createResume(UUID_2, "name2");
    private static final String UUID_3 = UUID.randomUUID().toString();
    public static final Resume RESUME_3 = createResume(UUID_3, "name3");
    private static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_4 = createResume(UUID_4, "name4");
    private static final String UUID_NOT_EXIST = "dummy";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    protected void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
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
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[0], storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    @DisplayName("Тест метода update")
    public void update() {
        Resume resume = new Resume(UUID_1, "New name");
        resume.setContact(ContactType.PHONE, "52145656");
        storage.update(resume);
        Assertions.assertEquals(resume, storage.get(UUID_1));
    }

    @Test
    @DisplayName("Тест на отсутствие обновления")
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    @DisplayName("Тест метода save")
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    @DisplayName("Тест метода get")
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    @DisplayName("Тест на отсутствие резюме")
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    @DisplayName("Тест метода delete")
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    @DisplayName("Тест на удаление несуществующего резюме")
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    @DisplayName("Тест метода getAllSorted")
    public void getAllSorted() {
        List<Resume> expected = storage.getAllSorted();
        List<Resume> actual = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест метода size")
    public void size() {
        assertSize(3);
    }
}
