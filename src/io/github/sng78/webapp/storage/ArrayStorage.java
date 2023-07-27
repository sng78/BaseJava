package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_CAPACITY = 10000;
    private final Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int numberResumes;

    public void clear() {
        Arrays.fill(storage, 0, numberResumes, null);
        numberResumes = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.uuid);
        if (index != -1) {
            storage[index] = resume;
        } else {
            printNoResume(resume.uuid);
        }
    }

    public void save(Resume resume) {
        if (numberResumes == STORAGE_CAPACITY) {
            System.out.printf("ОШИБКА! РЕЗЮМЕ %s НЕ ДОБАВЛЕНО, ПРЕВЫШЕН ЛИМИТ!\n", resume);
        } else if (findIndex(resume.uuid) != -1) {
            System.out.printf("ОШИБКА! РЕЗЮМЕ %s УЖЕ ЕСТЬ В БАЗЕ ДАННЫХ!\n", resume);
        } else {
            storage[numberResumes] = resume;
            numberResumes++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        printNoResume(uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberResumes);
    }

    public int size() {
        return numberResumes;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < numberResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void printNoResume(String uuid) {
        System.out.printf("ОШИБКА! РЕЗЮМЕ %s ОТСУТСТВУЕТ В БАЗЕ ДАННЫХ!\n", uuid);
    }
}
