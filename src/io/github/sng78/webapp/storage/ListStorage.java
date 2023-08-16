package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    protected int getSearchUuid(String uuid) {
        for (Resume resume : listStorage) {
            if (resume.getUuid().equals(uuid)) {
                return listStorage.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Resume resume, int searchUuid) {
        listStorage.set(searchUuid, resume);
    }

    @Override
    protected void saveResume(Resume resume, int searchUuid) {
        listStorage.add(resume);
    }

    @Override
    protected Resume getResume(int searchUuid) {
        return listStorage.get(searchUuid);
    }

    @Override
    protected void deleteResume(int searchUuid) {
        listStorage.remove(searchUuid);
    }
}
