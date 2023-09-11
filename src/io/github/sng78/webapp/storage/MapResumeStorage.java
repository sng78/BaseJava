package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume resume, Resume searchKey) {
        saveResume(resume, searchKey);
    }

    @Override
    protected void saveResume(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected List<Resume> getStorageAsList() {
        return new ArrayList<>(storage.values());
    }
}
