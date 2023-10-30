package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listResumes = Arrays.asList(Arrays.copyOf(storage, numberResumes));
        Collections.sort(listResumes);
        return listResumes;
//        return Arrays.asList(Arrays.copyOf(storage, numberResumes));
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
        return Arrays.binarySearch(storage, 0, numberResumes, searchKey, RESUME_COMPARATOR);
    }
}
