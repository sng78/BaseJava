package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public List<Resume> getAllSorted() {
        return Arrays.asList(Arrays.copyOf(storage, numberResumes));
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        searchKey = (Integer) searchKey * -1 - 1;
        System.arraycopy(storage, (Integer) searchKey, storage,
                (Integer) searchKey + 1, numberResumes - (Integer) searchKey);
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected void removeResume(Object searchKey) {
        System.arraycopy(storage, (Integer) searchKey + 1, storage, (Integer) searchKey,
                numberResumes - 1 - (Integer) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, numberResumes, searchKey);
    }
}
