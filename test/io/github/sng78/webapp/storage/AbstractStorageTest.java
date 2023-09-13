package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.github.sng78.webapp.ResumeTestData.createResume;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = createResume(UUID_1, "name1");
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = createResume(UUID_2, "name2");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = createResume(UUID_3, "name3");
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = createResume(UUID_4, "name4");
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
        storage.update(RESUME_2);
        Assertions.assertSame(RESUME_2, storage.get(UUID_2));
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
        Assertions.assertEquals(expected, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    @DisplayName("Тест метода size")
    public void size() {
        assertSize(3);
    }
}
