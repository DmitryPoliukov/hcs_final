<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="by.epamtc.poliukov.entity.User" scope="request"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>

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

<div class="container-fluid text-center wrapper">
    <br class="row content">

    <%--<c:import url="../menu/adminMenu.jsp"/>--%>

    <div class="col-md-12 text-cente mainContent">
        <div>
            <h1><c:out value="${user.login}"/> profile</h1>
            <div class="col-md-12">
                <c:if test="${not empty param.errorMessage}">
                    <h4 class="red"><c:out value="${param.errorMessage}"/></h4>
                </c:if>
                <div class="container-fluid">
                    <table class="table table-striped">
                        <tbody>
                        <tr>
                            <td>Login</td>
                            <td>${user.login}</td>

                        </tr>
                        <tr>
                            <td>Name</td>
                            <td>${user.name}</td>
                        </tr>
                        <tr>
                            <td>Second name</td>
                            <td>${user.secondName}</td>

                        </tr>
                        <tr>
                            <td>Surname</td>
                            <td>${user.surname}</td>

                        </tr>
                        <tr>
                            <td>Email</td>
                            <td>${user.email}</td>

                        </tr>
                        <tr>
                            <td>Phone</td>
                            <td>${user.phone}</td>

                        </tr>
                        </tbody>
                    </table>
                </div>
        </div>

    </div>


</div>

    <a href="DispatcherServlet?command=add-tenant" class="btn btn-info" role="button">Link Button</a>

<div class="footer">
    <jsp:include page="../menu/footer.jsp"/>
</div>

</body>
</html>
