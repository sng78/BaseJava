package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insertResume(Resume resume, int index) {
        storage[numberResumes] = resume;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[numberResumes - 1];
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
