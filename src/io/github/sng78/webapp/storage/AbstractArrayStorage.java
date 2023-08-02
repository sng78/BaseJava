package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_CAPACITY = 10000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberResumes = 0;

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        printNoResume(uuid);
        return null;
    }

    public int size() {
        return numberResumes;
    }

    protected abstract void printNoResume(String uuid);

    protected abstract int getIndex(String uuid);
}
