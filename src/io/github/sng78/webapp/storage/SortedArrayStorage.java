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
    protected void insertResume(Resume resume, Integer searchKey) {
        searchKey = searchKey * -1 - 1;
        System.arraycopy(storage, searchKey, storage,
                searchKey + 1, numberResumes - searchKey);
        storage[searchKey] = resume;
    }

    @Override
    protected void removeResume(Integer searchKey) {
        System.arraycopy(storage, searchKey + 1, storage, searchKey,
                numberResumes - 1 - searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, numberResumes, searchKey);
    }
}
