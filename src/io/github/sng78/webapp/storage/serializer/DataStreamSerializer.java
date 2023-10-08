package io.github.sng78.webapp.storage.serializer;

import io.github.sng78.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, resume.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getValue());
                        break;
                    case ACHIEVEMENT:
                    case SKILLS:
                        writeWithException(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(dos, ((OrganizationSection) section).getItems(), organization -> {
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(organization.getWebsite());
                            writeWithException(dos, organization.getPeriods(), period -> {
                                dos.writeUTF(String.valueOf(period.getStartDate()));
                                dos.writeUTF(String.valueOf(period.getEndDate()));
                                dos.writeUTF(period.getPosition());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                }
            });
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readItems(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, DataWriter<T> writer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private void readItems(DataInputStream dis, DataProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case SKILLS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readList(dis, () ->
                        new Organization(dis.readUTF(), dis.readUTF(), readList(dis, () ->
                                new Period(LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())))));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> List<T> readList(DataInputStream dis, DataReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface DataReader<T> {
        T read() throws IOException;
    }

    private interface DataWriter<T> {
        void write(T t) throws IOException;
    }

    private interface DataProcessor {
        void process() throws IOException;
    }
}
