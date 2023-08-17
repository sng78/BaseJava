package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        saveResume(resume, searchKey);
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return mapStorage.get((String) searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        mapStorage.remove((String) searchKey);
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapStorage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }
}
