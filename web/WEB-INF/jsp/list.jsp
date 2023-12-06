<%@ page import="io.github.sng78.webapp.model.ContactType" %>
<%@ page import="io.github.sng78.webapp.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text.html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table id="hidden-table">
        <tr>
            <td style="border-bottom: hidden; text-align: left">
                <a href="resume?action=add">
                    <button><img src="img/add.png" alt="" style="vertical-align:middle"> Новое резюме</button>
                </a>
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="io.github.sng78.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>
                    <%=
                    resume.getContact(ContactType.EMAIL) != null ?
                            ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL)).substring(7) : ""
                    %>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete">
                        <c:set var="notEditUuid" value="<%=Config.get().getProtectedUuid()%>"/>
                        <jsp:useBean id="notEditUuid" type="java.lang.String"/>
                        <c:choose>
                            <c:when test="<%=resume.getUuid().equals(notEditUuid)%>">
                                <button disabled><img src="img/delete.png" alt="" style="vertical-align:middle"> Удалить</button>
                            </c:when>
                            <c:otherwise>
                                <button><img src="img/delete.png" alt="" style="vertical-align:middle"> Удалить</button>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">
                    <button><img src="img/pencil.png" alt="" style="vertical-align:middle"> Редактировать</button>
                </a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
