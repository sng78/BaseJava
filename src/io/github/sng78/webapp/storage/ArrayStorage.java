package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void deleteDifferPart(int index) {
        storage[index] = storage[numberResumes - 1];
        storage[numberResumes - 1] = null;
    }

    @Override
    protected void saveDifferPart(Resume resume) {
        storage[numberResumes] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < numberResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
