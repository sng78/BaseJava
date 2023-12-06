package io.github.sng78.webapp;

import io.github.sng78.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        String uuid1 = Config.get().getProtectedUuid();
        String fullName1 = "Сергей Горбачев";
        Resume resume = createResume(uuid1, fullName1);

        printFullName(resume);
        printContacts(resume);
        printAllSections(resume);
    }

    public static Resume createResume(String uuid, String fullName) {
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
        resume.setContact(ContactType.PHONE, "+7(985)206-36-30");
        resume.setContact(ContactType.TELEGRAM, "t.me/sngor78");
        resume.setContact(ContactType.EMAIL, "sng78@list.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/sergei-gorbachev/");
        resume.setContact(ContactType.GITHUB, "https://github.com/sng78");
        resume.setContact(ContactType.WEBSITE, "https://t.me/sngor78");
    }

    private static void fillObjectiveSection(Resume resume) {
        Section objective = new TextSection("Java-разработчик");
        resume.setSection(SectionType.OBJECTIVE, objective);
    }

    private static void fillPersonalSection(Resume resume) {
        Section personal = new TextSection("Аналитический склад ума, стремление к обучению, " +
                "упорство и доведение работы до конца, умение декомпозировать задачи");
        resume.setSection(SectionType.PERSONAL, personal);
    }

    private static void fillAchievementSection(Resume resume) {
        List<String> achievementList = new ArrayList<>();
        achievementList.add("<b>База данных резюме (web-приложение): </b>" +
                "<br>Реализованы различные способы хранения резюме: " +
                "<br>- в сортированном и не сортированном массиве, " +
                "<br>- в коллекциях (List, Map), " +
                "<br>- в файловой системе (с использованием File и Path API, в стандартной и кастомной сериализации Java, в формате JSON (Gson), в формате XML (JAXB)), " +
                "<br>- в реляционной базе PostgreSQL\n");
        achievementList.add("<b>Англо-русский словарь: </b>" +
                "<br>Разработал приложение для составления англо-русского словаря по файлу книги. " +
                "<br>Идея для данного приложения возникла у меня при изучении английского языка. " +
                "<br>Я подумал, что неплохо было бы при чтении англоязычных книг иметь словарь всех слов, которые встречаются в книге. " +
                "<br>Его можно распечатать и использовать при чтении, а можно просто выучить незнакомые слова.");
        achievementList.add("<b>База данных работников: </b>" +
                "<br>Разработал CRUD-приложение для работы с базой данных работников. " +
                "<br>Программа позволяет выводить список работников из базы данных на экран (MySQL), добавлять/удалять работника, редактировать его данные. " +
                "<br>Действия выполняются через браузер, использовал сервер Tomcat.\n");
        achievementList.add("<b>Телеграм-бот NASA:</b>" +
                "<br>Разработал приложение для получения астрономических изображений NASA с помощью NASA API. " +
                "<br>Интегрировал его в Telegram-бота, который по запросу публикует фотографию из космоса (изображения на сайте обновляются раз в сутки).\n");
        Section achievement = new ListSection(achievementList);
        resume.setSection(SectionType.ACHIEVEMENT, achievement);
    }

    private static void fillSkillsSection(Resume resume) {
        List<String> skillsList = new ArrayList<>();
        skillsList.add("Языки программирования: Java (Time API, Streams, java.util.logging, многопоточность), " +
                "Python, HTML / CSS");
        skillsList.add("Система автоматической сборки: Maven, Gradle");
        skillsList.add("Контроль версий: Git / GitHub");
        skillsList.add("Java-фреймворки: Spring (Core, MVC, Security, Boot, Data), Hibernate");
        skillsList.add("Тестирование: JUnit, Mockito");
        skillsList.add("Базы данных: PostgreSQL, MySQL");
        skillsList.add("Технологии: REST API, Servlet, JSP / JSTL, XML, JAXB, JSON, GSON");
        skillsList.add("Инструменты: IntelliJ IDEA, DBeaver, Apache Tomcat");
        skillsList.add("ОС: Linux");
        skillsList.add("Знание и опыт применения концепций ООП, SOA, шаблонов проектирования, " +
                "UML, функционального программирования");
        skillsList.add("Русский - родной, английский - A1 beginner");
        Section skills = new ListSection(skillsList);
        resume.setSection(SectionType.SKILLS, skills);
    }

    private static void fillExperienceSection(Resume resume) {
        Period period1 = new Period(
                LocalDate.of(2023, 4, 1),
                LocalDate.now(),
                "Java-разработчик",
                "Разработал web-приложение, реализуя разные способы хранения резюме, а также его деплой на выделенный сервер");
        Period period2 = new Period(
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 3, 1),
                "Стажер",
                "Разработал приложение по загрузке и обработке справочника городов России для B2B-сервиса СберБизнес.");
        Period period3 = new Period(
                LocalDate.of(2011, 8, 1),
                LocalDate.now(),
                "Главный эксперт, эксперт, ведущий инженер",
                "Управлял портфелем проектов в 2 млрд руб., " +
                        "ввел в эксплуатацию порядка 10 объектов, " +
                        "подготовка и проверка исполнительной документации в строительстве, " +
                        "списание материалов");
        List<Organization> workOrganizations = new ArrayList<>();
        workOrganizations.add(new Organization("Java Online Projects", "https://javaops.ru/", period1));
        workOrganizations.add(new Organization("Виртуальная стажировка в Сбере от компании Преактум", "https://v.preactum.ru/stazhirovki/sberbank-java-razrabotka/", period2));
        workOrganizations.add(new Organization("Работа в строительных компаниях ООО “ДМУ”, ООО “РСК”, ООО “Стройтрансгаз-М”", "", period3));
        Section experience = new OrganizationSection(workOrganizations);
        resume.setSection(SectionType.EXPERIENCE, experience);
    }

    private static void fillEducationSection(Resume resume) {
        Period period9 = new Period(
                LocalDate.of(2022, 5, 1),
                LocalDate.of(2022, 12, 1),
                "",
                "Диплом 'Разработчик на языке Java'");
        Period period10 = new Period(
                LocalDate.of(2000, 9, 1),
                LocalDate.of(2006, 6, 1),
                "",
                "Диплом 'Инженер по специальности Промышленное и гражданское строительство'");
        Period period11 = new Period(
                LocalDate.of(2022, 5, 1),
                LocalDate.of(2022, 12, 1),
                "",
                "Курс 'Spring для начинающих'. Документ об образовании не предусмотрен");
        Period period12 = new Period(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 1),
                "",
                "Курс 'Web, Spring, Spring MVC'. Выдано свидетельство");
        Period period13 = new Period(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 1),
                "",
                "Курс 'Основы SQL'. Выдано свидетельство");
        Period period14 = new Period(
                LocalDate.of(2021, 9, 1),
                LocalDate.of(2021, 10, 1),
                "",
                "Курс 'Основы Python'. Выдан сертификат");
        Period period15 = new Period(
                LocalDate.of(2016, 3, 1),
                LocalDate.of(2016, 3, 1),
                "",
                "Тренинг 'Тайм-менеджмент'. Выдан сертификат");

        List<Organization> educationOrganizations = new ArrayList<>();
        educationOrganizations.add(new Organization("Нетология",
                "https://netology.ru/programs/java-developer", period9));
        educationOrganizations.add(new Organization("МГСУ",
                "", period10));
        educationOrganizations.add(new Organization("Stepik",
                "https://stepik.org/course/115372/info", period11));
        educationOrganizations.add(new Organization("Нетология",
                "https://netology.ru/sharing/c5a2359ece65e26188c6164c2cb5f1db?utm_source=social&utm_campaign=achievements", period12));
        educationOrganizations.add(new Organization("Нетология",
                "https://netology.ru/programs/vvedenie-v-sql-i-rabotu-s-bazoi-dannih", period13));
        educationOrganizations.add(new Organization("Stepik",
                "https://stepik.org/course/58852/info", period14));
        educationOrganizations.add(new Organization("Профессионалы.ru",
                "", period15));
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
