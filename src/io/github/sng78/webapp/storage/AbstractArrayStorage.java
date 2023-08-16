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
    protected void saveResume(Resume resume, int searchUuid) {
        if (numberResumes == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertResume(resume, searchUuid);
            numberResumes++;
        }
    }

    //delete
    @Override
    protected void deleteResume(int searchUuid) {
        removeResume(searchUuid);
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
    protected void updateResume(Resume resume, int searchUuid) {
        storage[searchUuid] = resume;
    }

    @Override
    protected Resume getResume(int searchUuid) {
        return storage[searchUuid];
    }

    protected abstract void insertResume(Resume resume, int searchUuid);

    protected abstract void removeResume(int searchUuid);
}
