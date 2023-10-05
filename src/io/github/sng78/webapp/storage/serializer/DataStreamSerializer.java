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
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case SKILLS:
                        writeWithException(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    default:
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

            loadContacts(resume, dis);
            loadSections(resume, dis);

            return resume;
        }
    }

    private static void loadContacts(Resume resume, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private static void loadSections(Resume resume, DataInputStream dis) throws IOException {
        int numberSections = dis.readInt();
        SectionType sectionType;
        for (int i = 0; i < numberSections; i++) {
            sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    String text = dis.readUTF();
                    resume.setSection(sectionType, new TextSection(text));
                    break;
                case ACHIEVEMENT:
                case SKILLS:
                    List<String> skills = new ArrayList<>();
                    int numberSkills = dis.readInt();
                    for (int j = 0; j < numberSkills; j++) {
                        String skill = dis.readUTF();
                        skills.add(skill);
                    }
                    resume.setSection(sectionType, new ListSection(skills));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    int numberOrganizations = dis.readInt();
                    for (int j = 0; j < numberOrganizations; j++) {
                        String organization = dis.readUTF();
                        String website = dis.readUTF();
                        int numberPeriods = dis.readInt();
                        Period[] periods = new Period[numberPeriods];
                        for (int k = 0; k < periods.length; k++) {
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            String position = dis.readUTF();
                            String description = dis.readUTF();
                            periods[k] = new Period(startDate, endDate, position, description);
                        }
                        organizations.add(new Organization(organization, website, periods));
                    }
                    resume.setSection(sectionType, new OrganizationSection(organizations));
            }
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, DataWriter<T> writer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private interface DataWriter<T> {
        void write(T t) throws IOException;
    }
}
