package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_CAPACITY = 10000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberResumes = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, numberResumes, null);
        numberResumes = 0;
    }

    @Override
    protected void updateResume(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected void saveResume(Resume resume, Integer searchKey) {
        if (numberResumes == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow ", resume.getUuid());
        } else {
            insertResume(resume, searchKey);
            numberResumes++;
        }
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        removeResume(searchKey);
        storage[numberResumes - 1] = null;
        numberResumes--;
    }

    @Override
    public int size() {
        return numberResumes;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getStorageAsList() {
        return Arrays.asList(Arrays.copyOf(storage, numberResumes));
    }

    protected abstract void insertResume(Resume resume, Integer searchKey);

    protected abstract void removeResume(Integer searchKey);
}
