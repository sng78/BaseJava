package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insertResume(Resume resume, int searchUuid) {
        searchUuid = searchUuid * -1 - 1;
        System.arraycopy(storage, searchUuid, storage, searchUuid + 1, numberResumes - searchUuid);
        storage[searchUuid] = resume;
    }

    @Override
    protected void removeResume(int searchUuid) {
        System.arraycopy(storage, searchUuid + 1, storage, searchUuid, numberResumes - 1 - searchUuid);
    }

    @Override
    protected int getSearchUuid(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, numberResumes, searchKey);
    }
}
