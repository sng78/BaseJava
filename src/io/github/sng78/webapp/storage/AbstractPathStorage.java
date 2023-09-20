package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.exception.StorageException;
import io.github.sng78.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
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
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("IO error ", path.getFileName() + " ", e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        File file = path.toFile();
        try {
            if (!file.createNewFile()) {
                throw new StorageException("File already exists: ", file.getName());
            } else {
                updateResume(resume, path);
            }
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName() + " ", e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        File file = path.toFile();
        try {
            return readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName() + " ", e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        File file = path.toFile();
        if (!file.delete()) {
            throw new StorageException("Cannot delete file: ", file.getName());
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        File file = new File(directory.toFile(), uuid);
        return Paths.get(String.valueOf(file));
    }

    @Override
    protected boolean isExist(Path path) {
        return path.toFile().exists();
    }

    @Override
    protected List<Resume> getStorageAsList() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getListFiles()) {
            resumes.add(getResume(file.toPath()));
        }
        return resumes;
    }

    @Override
    public void clear() {
        try (Stream<Path> filesList = Files.list(directory)) {
            filesList.forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] files = directory.toFile().listFiles();
        if (files == null) {
            throw new StorageException("Cannot read directory: " + directory.toFile().getName(), null);
        }
        return files;
    }

    protected abstract void writeResume(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}
