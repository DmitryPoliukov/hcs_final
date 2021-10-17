<%--
  Created by IntelliJ IDEA.
  User: radof
  Date: 17.10.2021
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="by.epamtc.poliukov.entity.User" scope="request"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set scope="session" var="previousQuery" value="index.jsp"/>

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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('successMessage')!=null}">
    <h3 class="green"><c:out value="${sessionScope.get('login')} ${requestScope.get('successMessage')} ${sessionScope.get('role')}"/></h3>
    <c:remove var="successMessage" scope="request"/>
</c:if>


<div class="container">
    <h2>Please, enter user login</h2>

    <form action="DispatcherServlet" method="post">
        <input type="hidden" name="command" value="update-role"/>

        <label for="login">login<br></label>
        <input id="login" class="form-control" type="text" name="login" required/>
        <br/>


        <br>
        <br>
        <br>
        <h2>Choose new role</h2>
        <label><input type="radio" name="role" value="admin" checked/>admin</label>
        <label><input type="radio" name="role" value="tenant" checked/>tenant</label>
        <label><input type="radio" name="role" value="employee" checked/>employee</label>
        <label><input type="radio" name="role" value="dispatcher" checked/>dispatcher</label>


        <button type="submit" class="btn btn-primary">Update</button>
    </form>

</div>
<c:import url="../menu/footer.jsp"/>

</body>
</html>
