<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="io.github.sng78.webapp.model.ListSection" %>
<%@ page import="io.github.sng78.webapp.model.OrganizationSection" %>
<%@ page import="io.github.sng78.webapp.model.TextSection" %>
<%@ page import="io.github.sng78.webapp.utils.DateUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text.html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" scope="request" type="io.github.sng78.webapp.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h3>${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit">
        <button><img src="img/pencil.png" alt="" style="vertical-align:middle"> Редактировать</button>
    </a></h3>
    <ul>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean
                    id="contactEntry"
                    type="java.util.Map.Entry<io.github.sng78.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            <br>
        </c:forEach>
    </ul>
    <br>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean
                id="sectionEntry"
                type="java.util.Map.Entry<io.github.sng78.webapp.model.SectionType,
                io.github.sng78.webapp.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="io.github.sng78.webapp.model.Section"/>
        <h3>${type.title}</h3>

        <c:choose>
            <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                <ul>
                    <%=((TextSection) section).getValue()%>
                </ul>
                <br>
            </c:when>

            <c:when test="${type == 'ACHIEVEMENT' || type == 'SKILLS'}">
                <ul>
                    <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
                <br>
            </c:when>

            <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                <ul>
                    <c:forEach var="item" items="<%=((OrganizationSection) section).getItems()%>">
                        <c:choose>
                            <c:when test="${empty item.website}">
                                <b>${item.name}</b>
                            </c:when>
                            <c:otherwise>
                                <a href="${item.website}" target="_blank"><b>${item.name}</b></a>
                            </c:otherwise>
                        </c:choose>
                        <br>
                        <c:forEach var="period" items="${item.periods}">
                            <jsp:useBean id="period" type="io.github.sng78.webapp.model.Period"/>
                            ${DateUtil.format(period.startDate)} - ${DateUtil.format(period.endDate)}<br>
                            <c:set var="position" value="${period.position}"/>
                            <c:if test="${position.length() != 0}">
                                <b>${position}</b><br>
                            </c:if>
                            ${period.description}<br>
                        </c:forEach>
                        <br>
                    </c:forEach>
                </ul>
            </c:when>
        </c:choose>
        <hr>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
