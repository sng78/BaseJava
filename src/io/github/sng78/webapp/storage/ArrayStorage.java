package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        Arrays.fill(storage, 0, numberResumes, null);
        numberResumes = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            printNoResume(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (numberResumes == STORAGE_CAPACITY) {
            System.out.printf("ОШИБКА! РЕЗЮМЕ %s НЕ ДОБАВЛЕНО, ПРЕВЫШЕН ЛИМИТ!\n", resume);
        } else if (getIndex(resume.getUuid()) != -1) {
            System.out.printf("ОШИБКА! РЕЗЮМЕ %s УЖЕ ЕСТЬ В БАЗЕ ДАННЫХ!\n", resume);
        } else {
            storage[numberResumes] = resume;
            numberResumes++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[numberResumes - 1];
            storage[numberResumes - 1] = null;
            numberResumes--;
        } else {
            printNoResume(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberResumes);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < numberResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void printNoResume(String uuid) {
        System.out.printf("ОШИБКА! РЕЗЮМЕ %s ОТСУТСТВУЕТ В БАЗЕ ДАННЫХ!\n", uuid);
    }
}
