package io.github.sng78.webapp.storage.serializer;

import io.github.sng78.webapp.model.ContactType;
import io.github.sng78.webapp.model.Resume;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            //TODO implements sections
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            //TODO implements sections


            return resume;
        }
    }
}
