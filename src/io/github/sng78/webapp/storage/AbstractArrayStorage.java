package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_CAPACITY = 10000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberResumes = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, numberResumes, null);
        numberResumes = 0;
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        if (numberResumes == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertResume(resume, searchKey);
            numberResumes++;
        }
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void deleteResume(Object searchKey) {
        removeResume(searchKey);
        storage[numberResumes - 1] = null;
        numberResumes--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberResumes);
    }

    @Override
    public int size() {
        return numberResumes;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insertResume(Resume resume, Object searchKey);

    protected abstract void removeResume(Object searchKey);
}
