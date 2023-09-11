package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName() + " ", e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            boolean created = file.createNewFile();
            if (!created) {
                throw new StorageException("File already exists: ", file.getName());
            } else {
                writeResume(resume, file);
            }
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName() + " ", e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readResume(file);
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName() + " ", e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        boolean deleted = file.delete();
        if (!deleted) {
            throw new StorageException("Cannot delete file: ", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getStorageAsList() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getListFiles()) {
            try {
                resumes.add(readResume(file));
            } catch (IOException e) {
                throw new StorageException("IO error ", file.getName() + " ", e);
            }
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = getListFiles();
        for (File file : files) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        return directory.listFiles();
    }

    protected abstract Resume readResume(File file) throws IOException;

    protected abstract void writeResume(Resume resume, File file) throws IOException;
}
