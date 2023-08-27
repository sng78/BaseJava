package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    @DisplayName("Тест на переполнение хранилища")
    public void overflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_CAPACITY; i++) {
                storage.save(new Resume("" + i, "New name"));
            }
        } catch (Exception e) {
            Assertions.fail("storage overflow occurred ahead of time - " + e);
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume("New name")));
    }
}
