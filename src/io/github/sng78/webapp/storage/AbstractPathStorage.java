package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable/writable");
        }
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File save error ", e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File create error ", e);
        }
        updateResume(resume, path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error ", e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error ", e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> getStorageAsList() {
        return getListFiles().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getListFiles().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getListFiles().count();
    }

    private Stream<Path> getListFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

    protected abstract void writeResume(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}
