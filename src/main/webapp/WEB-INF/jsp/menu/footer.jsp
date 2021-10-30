
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="locale" var="locale"/>
        <fmt:message bundle="${locale}" key="locale.footer.info" var="info"/>

        <nav class="navbar fixed-bottom navbar-dark bg-dark">
        <a class="navbar-brand" href="#">${info}</a>
        </nav>

