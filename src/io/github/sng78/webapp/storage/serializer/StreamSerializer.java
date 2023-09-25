package io.github.sng78.webapp.storage.serializer;

import io.github.sng78.webapp.model.Resume;

import java.io.*;

public interface StreamSerializer {
    void writeResume(Resume resume, OutputStream os) throws IOException;

    Resume readResume(InputStream is) throws IOException;
}
