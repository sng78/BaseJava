package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;

import java.io.*;
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
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File save error ", e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("File create error");
            } else {
                updateResume(resume, file);
            }
        } catch (IOException e) {
            throw new StorageException("IO error ", e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error ", e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error");
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
            resumes.add(getResume(file));
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
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Cannot read directory");
        }
        return files;
    }

    protected abstract void writeResume(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}
