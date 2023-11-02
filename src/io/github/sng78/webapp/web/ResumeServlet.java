package io.github.sng78.webapp.web;

import io.github.sng78.webapp.Config;
import io.github.sng78.webapp.model.ContactType;
import io.github.sng78.webapp.model.Resume;
import io.github.sng78.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();

        //test data
        storage.clear();
        Resume resume1 = new Resume("uuid1", "name1");
        resume1.setContact(ContactType.EMAIL, "name1@email.com");
        Resume resume2 = new Resume("uuid2", "name2");
        Resume resume3 = new Resume("uuid3", "name3");
        resume3.setContact(ContactType.EMAIL, "name3@email.com");
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>\n" +
                    "<head>\n" +
                    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                    "    <title>List of all resumes</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<section>\n" +
                    "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                    "    <tr>\n" +
                    "        <th>Имя</th>\n" +
                    "        <th>Email</th>\n" +
                    "    </tr>\n");
        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>\n" +
                        "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
                        "     <td>" + resume.getContact(ContactType.EMAIL) + "</td>\n" +
                        "</tr>\n");
        }
        writer.write(
                "</table>\n" +
                    "</section>\n" +
                    "</body>\n" +
                    "</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
