<%@ page import="io.github.sng78.webapp.utils.DateUtil" %>
<%@ page import="io.github.sng78.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отмена</button>
        <hr>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=31 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="io.github.sng78.webapp.model.Section"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                    <input type="text" name="${type}" size=100 value="<%=section%>">
                </c:when>

                <c:when test="${type == 'ACHIEVEMENT' || type == 'SKILLS'}">
                    <textarea name="${type}" cols=100
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>

                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getItems()%>"
                               varStatus="counter">
                        <dl>
                            <dt><b>Организация / сайт</b></dt>
                            <dd>
                                <input type="text" name="${type}" size=31 value="${organization.name}">
                            </dd>
                            <dd>
                                <input type="text" name="${type}_url" size=31 value="${organization.website}">
                            </dd>
                        </dl>

                        <div style="margin-left: 30px">
                            <c:forEach var="period" items="${organization.periods}">
                                <jsp:useBean id="period" type="io.github.sng78.webapp.model.Period"/>
                                <dl>
                                    <dt>Дата начала / окончания</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}_startDate" size=12
                                               value="<%=DateUtil.format(period.getStartDate())%>"
                                               placeholder="MM/YYYY">
                                    </dd>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}_endDate" size=12
                                               value="<%=DateUtil.format(period.getEndDate())%>"
                                               placeholder="MM/YYYY">
                                    </dd>
                                </dl>
                                <c:if test="${type == 'EXPERIENCE'}">
                                    <dl>
                                        <dt>Позиция</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}_position" size=100
                                                   value="${period.position}">
                                        </dd>
                                    </dl>
                                </c:if>
                                <dl>
                                    <dt>Описание</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}_description" size=100
                                               value="${period.description}">
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отмена</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
