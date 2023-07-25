package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int numberResumes;

    public void clear() {
        for (int i = 0; i < numberResumes; i++) {
            storage[i] = null;
        }
        numberResumes = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.uuid);
        if (index != -1) {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (findIndex(resume.uuid) == -1) {
            if (numberResumes < storage.length) {
                System.out.println("Добавляю новое резюме");
                storage[numberResumes] = resume;
                numberResumes++;
            } else {
                System.out.printf("ОШИБКА! Резюме %s не добавлено, превышен лимит!\n", resume);
            }
        } else {
            System.out.printf("ОШИБКА! Резюме %s уже есть в базе данных!\n", resume);
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        return index != -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[numberResumes - 1];
            storage[numberResumes - 1] = null;
            numberResumes--;
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
        System.out.printf("ОШИБКА! Резюме %s отсутствует в базе данных!\n", uuid);
        return -1;
    }
}
