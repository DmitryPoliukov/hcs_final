<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="user" class="by.epamtc.poliukov.entity.User" scope="request"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.userPage.profile" var="profile"/>
<fmt:message bundle="${locale}" key="locale.common.name" var="name"/>
<fmt:message bundle="${locale}" key="locale.common.secondName" var="secondName"/>
<fmt:message bundle="${locale}" key="locale.common.surname" var="surname"/>
<fmt:message bundle="${locale}" key="locale.common.email" var="email"/>
<fmt:message bundle="${locale}" key="locale.common.phone" var="phone"/>
<fmt:message bundle="${locale}" key="locale.common.username" var="username"/>
<fmt:message bundle="${locale}" key="locale.addSubquery.showMyRequests" var="showMyRequests"/>
<fmt:message bundle="${locale}" key="locale.userPage.role" var="role"/>



<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial scale=1">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Optional JavaScript -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <title>${user.login} profile</title>
</head>
<body>

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<div class="container-fluid text-center wrapper">
    <br class="row content">


    <div class="col-md-12 text-cente mainContent">
        <div>
            <h1><c:out value="${user.login}"/> ${profile}</h1>
            <div class="col-md-12">
                <c:if test="${not empty param.errorMessage}">
                    <h4 class="red"><c:out value="${param.errorMessage}"/></h4>
                </c:if>
                <div class="container-fluid">
                    <table class="table table-striped">
                        <tbody>
                        <tr>
                            <td>${username}</td>
                            <td>${user.login}</td>

                        </tr>
                        <tr>
                            <td>${name}</td>
                            <td>${user.name}</td>
                        </tr>
                        <tr>
                            <td>${secondName}</td>
                            <td>${user.secondName}</td>

                        </tr>
                        <tr>
                            <td>${surname}</td>
                            <td>${user.surname}</td>

                        </tr>
                        <tr>
                            <td>${email}</td>
                            <td>${user.email}</td>

                        </tr>
                        <tr>
                            <td>${phone}</td>
                            <td>${user.phone}</td>

                        </tr>
                        <tr>
                            <td>${role}</td>
                            <td>${user.role}</td>

                        </tr>


                        </tbody>
                    </table>
                </div>
        </div>

    </div>

</div>
    <c:if test='${sessionScope.get("user").role eq "tenant"}'>
        <a href="DispatcherServlet?command=show-all-tenant-requests" class="btn btn-info" role="button"> ${showMyRequests}</a>

    </c:if>


<div class="footer">
    <jsp:include page="../menu/footer.jsp"/>
</div>

</body>
</html>
