package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.ExistStorageException;
import io.github.sng78.webapp.exception.NotExistStorageException;
import io.github.sng78.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        int searchUuid = getSearchUuidExist(resume.getUuid());
        updateResume(resume, searchUuid);
    }

    @Override
    public void save(Resume resume) {
        int searchUuid = getSearchUuidNotExist(resume.getUuid());
        saveResume(resume, searchUuid);
    }

    @Override
    public Resume get(String uuid) {
        int searchUuid = getSearchUuidExist(uuid);
        return getResume(searchUuid);
    }

    @Override
    public void delete(String uuid) {
        int searchUuid = getSearchUuidExist(uuid);
        deleteResume(searchUuid);
    }

    protected int getSearchUuidExist(String uuid) {
        int searchUuid = getSearchUuid(uuid);
        if (!isExist(searchUuid)) {
            throw new NotExistStorageException(uuid);
        }
        return searchUuid;
    }

    protected int getSearchUuidNotExist(String uuid) {
        int searchUuid = getSearchUuid(uuid);
        if (isExist(searchUuid)) {
            throw new ExistStorageException(uuid);
        }
        return searchUuid;
    }

    protected boolean isExist(int index) {
        return index >= 0;
    }

    protected abstract void updateResume(Resume resume, int searchUuid);

    protected abstract void saveResume(Resume resume, int searchUuid);

    protected abstract Resume getResume(int searchUuid);

    protected abstract void deleteResume(int searchUuid);

    protected abstract int getSearchUuid(String uuid);
}
