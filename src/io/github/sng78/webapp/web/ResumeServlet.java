package io.github.sng78.webapp.web;

import io.github.sng78.webapp.Config;
import io.github.sng78.webapp.model.ContactType;
import io.github.sng78.webapp.model.Resume;
import io.github.sng78.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

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
        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
