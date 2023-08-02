package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void deleteDifferPart(int index) {
        System.arraycopy(storage, index + 1, storage, index, numberResumes - 1 - index);
        storage[numberResumes - 1] = null;
    }

    @Override
    protected void saveDifferPart(Resume resume) {
        int index = getIndex(resume.getUuid());
        index = index * -1 - 1;
        System.arraycopy(storage, index, storage, index + 1, numberResumes - index);
        storage[index] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, numberResumes, searchKey);
    }
}
