package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insertResume(Resume resume, int searchUuid) {
        storage[numberResumes] = resume;
    }

    @Override
    protected void removeResume(int searchUuid) {
        storage[searchUuid] = storage[numberResumes - 1];
    }

    @Override
    protected int getSearchUuid(String uuid) {
        for (int i = 0; i < numberResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
