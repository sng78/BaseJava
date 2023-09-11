package io.github.sng78.webapp;

import io.github.sng78.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        String uuid1 = "uuid1";
        String fullName1 = "Григорий Кислин";
        Resume resume = addResume(uuid1, fullName1);

        printFullName(resume);
        printContacts(resume);
        printAllSections(resume);
    }

    public static Resume addResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        fillContacts(resume);
        fillObjectiveSection(resume);
        fillPersonalSection(resume);
        fillAchievementSection(resume);
        fillSkillsSection(resume);
        fillExperienceSection(resume);
        fillEducationSection(resume);
        return resume;
    }

    private static void fillContacts(Resume resume) {
        resume.setContact(ContactType.PHONE, "+7(921)855-04-82");
        resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.setContact(ContactType.WEBSITE, "http://gkislin.ru/");
    }

    private static void fillObjectiveSection(Resume resume) {
        Section objective = new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objective);
    }

    private static void fillPersonalSection(Resume resume) {
        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personal);
    }

    private static void fillAchievementSection(Resume resume) {
        List<String> achievementList = new ArrayList<>();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов " +
                "на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для " +
                "комплексных DIY смет");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 " +
                "выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievementList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " +
                "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django).");
        achievementList.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        Section achievement = new ListSection(achievementList);
        resume.setSection(SectionType.ACHIEVEMENT, achievement);
    }

    private static void fillSkillsSection(Resume resume) {
        List<String> skillsList = new ArrayList<>();
        skillsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        skillsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        skillsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, " +
                "MS SQL, HSQLDB");
        skillsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        skillsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        skillsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, " +
                "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " +
                "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        skillsList.add("Python: Django.");
        skillsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        skillsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        skillsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, " +
                "XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, " +
                "OAuth2, JWT.");
        skillsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        skillsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, " +
                "iReport, OpenCmis, Bonita, pgBouncer");
        skillsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        skillsList.add("Родной русский, английский \"upper intermediate\"");
        Section skills = new ListSection(skillsList);
        resume.setSection(SectionType.SKILLS, skills);
    }

    private static void fillExperienceSection(Resume resume) {
        Period period1 = new Period(
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Period period2 = new Period(
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, " +
                        "Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                        "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Period period3 = new Period(
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная " +
                        "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация " +
                        "Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД " +
                        "и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C " +
                        "(WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                        "Интеграция Alfresco JLAN для online редактирование из браузера документов MS " +
                        "Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring " +
                        "MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote " +
                        "scripting via ssh tunnels, PL/Python");
        Period period4 = new Period(
                LocalDate.of(2010, 12, 1),
                LocalDate.of(2012, 4, 1),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                        "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                        "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), " +
                        "Highstock, Commet, HTML5");
        Period period5 = new Period(
                LocalDate.of(2008, 6, 1),
                LocalDate.of(2010, 12, 1),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, " +
                        "Maven2). Реализация администрирования, статистики и мониторинга фреймворка. " +
                        "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        Period period6 = new Period(
                LocalDate.of(2007, 3, 1),
                LocalDate.of(2008, 6, 1),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, " +
                        "JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        Period period7 = new Period(
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2007, 2, 1),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и " +
                        "отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        Period period8 = new Period(
                LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 " +
                        "(CHILL, ASM).");
        List<Organization> workOrganizations = new ArrayList<>();
        workOrganizations.add(new Organization("Java Online Projects", "http://javaops.ru/", period1));
        workOrganizations.add(new Organization("Wrike", "https://www.wrike.com/", period2));
        workOrganizations.add(new Organization("RIT Center", "none website", period3));
        workOrganizations.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                period4));
        workOrganizations.add(new Organization("Yota", "https://www.yota.ru/", period5));
        workOrganizations.add(new Organization("Enkata", "http://enkata.com/", period6));
        workOrganizations.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                period7));
        workOrganizations.add(new Organization("Alcatel", "http://www.alcatel.ru/", period8));
        Section experience = new OrganizationSection(workOrganizations);
        resume.setSection(SectionType.EXPERIENCE, experience);
    }

    private static void fillEducationSection(Resume resume) {
        Period period9 = new Period(
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "",
                "'Functional Programming Principles in Scala' by Martin Odersky");
        Period period10 = new Period(
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        Period period11 = new Period(
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2005, 4, 1),
                "",
                "3 месяца обучения мобильным IN сетям (Берлин)");
        Period period12 = new Period(
                LocalDate.of(1997, 9, 1),
                LocalDate.of(1998, 3, 1),
                "",
                "6 месяцев обучения цифровым телефонным сетям (Москва)");
        Period period13 = new Period(
                LocalDate.of(1993, 9, 1),
                LocalDate.of(1987, 9, 1),
                "",
                "Аспирантура (программист С, С++)");
        Period period14 = new Period(
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "",
                "Инженер (программист Fortran, C)");
        Period period15 = new Period(
                LocalDate.of(1984, 9, 1),
                LocalDate.of(1987, 6, 1),
                "",
                "Закончил с отличием");

        List<Organization> educationOrganizations = new ArrayList<>();
        educationOrganizations.add(new Organization("Coursera",
                "https://www.coursera.org/course/progfun", period9));
        educationOrganizations.add(new Organization("Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", period10));
        educationOrganizations.add(new Organization("Siemens AG",
                "http://www.siemens.ru/", period11));
        educationOrganizations.add(new Organization("Alcatel", "http://www.alcatel.ru/", period12));
        educationOrganizations.add(new Organization("Санкт-Петербургский национальный " +
                "исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/", period13, period14));
        educationOrganizations.add(new Organization("Заочная физико-техническая школа при МФТИ",
                "https://mipt.ru/", period15));
        Section education = new OrganizationSection(educationOrganizations);
        resume.setSection(SectionType.EDUCATION, education);
    }

    private static void printFullName(Resume resume) {
        System.out.println(resume.getFullName() + "\n");
    }

    private static void printContacts(Resume resume) {
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();
    }

    private static void printAllSections(Resume resume) {
        for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
            System.out.println("     " + entry.getKey().getTitle() + "\n" + entry.getValue());
        }
    }
}
