package io.github.sng78.webapp.web;

import io.github.sng78.webapp.Config;
import io.github.sng78.webapp.ResumeTestData;
import io.github.sng78.webapp.model.*;
import io.github.sng78.webapp.storage.Storage;
import io.github.sng78.webapp.utils.DateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();

        //test data
        storage.clear();
        Resume resume1 = ResumeTestData.createResume(UUID.randomUUID().toString(), "Григорий Кислин");
        Resume resume2 = new Resume("uuid2", "name2");
        Resume resume3 = new Resume("uuid3", "name3");
        resume3.setContact(ContactType.EMAIL, "name3@email.com");
        resume3.setContact(ContactType.PHONE, "555-55-55");
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = Resume.EMPTY;
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case SKILLS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganization = new ArrayList<>();
                            emptyFirstOrganization.add(Organization.EMPTY);
                            if (organizationSection != null) {
                                for (Organization organization : organizationSection.getItems()) {
                                    List<Period> emptyFirstPeriod = new ArrayList<>();
                                    emptyFirstPeriod.add(Period.EMPTY);
                                    emptyFirstPeriod.addAll(organization.getPeriods());
                                    emptyFirstOrganization.add(new Organization(
                                            organization.getName(), organization.getWebsite(), emptyFirstPeriod));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganization);
                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                        ("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.isEmpty());
        Resume r;
        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().isEmpty()) {
                r.getContacts().remove(type);
            } else {
                r.setContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case SKILLS:
                        r.setSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "_url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!isEmpty(name)) {
                                List<Period> periods = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "_startDate");
                                String[] endDates = request.getParameterValues(pfx + "_endDate");
                                String[] positions = request.getParameterValues(pfx + "_position");
                                String[] descriptions = request.getParameterValues(pfx + "_description");
                                for (int j = 0; j < startDates.length; j++) {
                                    if (!isEmpty(descriptions[j])) {
                                        periods.add(new Period(
                                                DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]),
                                                (positions == null ? "" : positions[j]), descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(name, urls[i], periods));
                            }
                        }
                        r.setSection(type, new OrganizationSection(organizations));
                        break;
                }
            }
        }

        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    private static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}
