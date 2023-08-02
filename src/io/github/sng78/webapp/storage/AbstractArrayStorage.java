package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_CAPACITY = 10000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberResumes = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, numberResumes, null);
        numberResumes = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            printNoResume(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (numberResumes == STORAGE_CAPACITY) {
            System.out.printf("ОШИБКА! РЕЗЮМЕ %s НЕ ДОБАВЛЕНО, ПРЕВЫШЕН ЛИМИТ!\n", resume);
        } else if (getIndex(resume.getUuid()) >= 0) {
            System.out.printf("ОШИБКА! РЕЗЮМЕ %s УЖЕ ЕСТЬ В БАЗЕ ДАННЫХ!\n", resume);
        } else {
            saveDifferPart(resume);
            numberResumes++;
        }
    }

    protected abstract void saveDifferPart(Resume resume);

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        printNoResume(uuid);
        return null;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteDifferPart(index);
            numberResumes--;
        } else {
            printNoResume(uuid);
        }
    }

    protected abstract void deleteDifferPart(int index);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberResumes);
    }

    @Override
    public int size() {
        return numberResumes;
    }

    protected abstract int getIndex(String uuid);

    protected void printNoResume(String uuid) {
        System.out.printf("ОШИБКА! РЕЗЮМЕ %s ОТСУТСТВУЕТ В БАЗЕ ДАННЫХ!\n", uuid);
    }
}
