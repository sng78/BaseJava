package io.github.sng78.webapp.storage.serializer;

import io.github.sng78.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            saveContacts(resume, dos);
            saveSections(resume, dos);
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

    private static void saveContacts(Resume resume, DataOutputStream dos) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private static void saveSections(Resume resume, DataOutputStream dos) throws IOException {
        Map<SectionType, Section> sections = resume.getSections();
        for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
            dos.writeUTF(section.getKey().name());
            switch (section.getKey()) {
                case OBJECTIVE:
                case PERSONAL:
                    dos.writeUTF(section.getValue().toString());
                    break;
                case ACHIEVEMENT:
                case SKILLS:
                    ListSection listItems = (ListSection) section.getValue();
                    dos.writeInt(listItems.getItems().size());
                    for (String item : listItems.getItems()) {
                        dos.writeUTF(item);
                    }
                    break;
                default:
                    OrganizationSection listOrganizations = (OrganizationSection) section.getValue();
                    dos.writeInt(listOrganizations.getItems().size());
                    for (Organization organization : listOrganizations.getItems()) {
                        dos.writeUTF(organization.getName());
                        dos.writeUTF(organization.getWebsite());
                        List<Period> periods = organization.getPeriods();
                        dos.writeInt(periods.size());
                        for (Period period : periods) {
                            dos.writeUTF(String.valueOf(period.getStartDate()));
                            dos.writeUTF(String.valueOf(period.getEndDate()));
                            dos.writeUTF(period.getPosition());
                            dos.writeUTF(period.getDescription());
                        }
                    }
            }
        }
    }

    private static void loadContacts(Resume resume, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private static void loadSections(Resume resume, DataInputStream dis) throws IOException {
        SectionType sectionType;
        List<Organization> organizations = new ArrayList<>();
        do {
            sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.setSection(sectionType, new TextSection(dis.readUTF()));
                    break;
                case ACHIEVEMENT:
                case SKILLS:
                    List<String> skills = new ArrayList<>();
                    for (int i = 0; i < dis.readInt(); i++) {
                        skills.add(dis.readUTF());
                    }
                    resume.setSection(sectionType, new ListSection(skills));
                    break;
                default:
                    for (int i = 0; i < dis.readInt(); i++) {
                        String organization = dis.readUTF();
                        String website = dis.readUTF();
                        Period[] periods = new Period[dis.readInt()];
                        for (int j = 0; j < periods.length; j++) {
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            String position = dis.readUTF();
                            String description = dis.readUTF();
                            periods[j] = new Period(startDate, endDate, position, description);
                        }
                        organizations.add(new Organization(organization, website, periods));
                    }
                    resume.setSection(sectionType, new OrganizationSection(organizations));
            }
        } while (sectionType != SectionType.EDUCATION);
    }
}
