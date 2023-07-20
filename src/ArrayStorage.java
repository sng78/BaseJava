import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int numberResume = 0;

    public void clear() {
        for (int i = 0; i < getAll().length; i++) {
            storage[i] = null;
        }
        numberResume = 0;
    }

    public void save(Resume resume) {
        if (numberResume < storage.length) {
            storage[numberResume] = resume;
            numberResume++;
        } else {
            System.out.println("Резюме не добавлено, превышен лимит!");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        return index != -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, numberResume - 1 - index);
            storage[numberResume - 1] = null;
            numberResume--;
        } else {
            System.out.println("Такого резюме в базе данных нет!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberResume);
    }

    public int size() {
        return getAll().length;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < getAll().length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
