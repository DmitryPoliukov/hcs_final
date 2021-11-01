<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.updateRole.admin" var="admin"/>
<fmt:message bundle="${locale}" key="locale.updateRole.tenant" var="tenant"/>
<fmt:message bundle="${locale}" key="locale.updateRole.employee" var="employee"/>
<fmt:message bundle="${locale}" key="locale.updateRole.dispatcher" var="dispatcher"/>
<fmt:message bundle="${locale}" key="locale.updateRole.submit" var="submit"/>
<fmt:message bundle="${locale}" key="locale.updateRole.enterLogin" var="enterLogin"/>
<fmt:message bundle="${locale}" key="locale.updateRole.chooseRole" var="chooseRole"/>
<fmt:message bundle="${locale}" key="locale.updateRole.login" var="login"/>


<!DOCTYPE html>
<html>
<head>
    <title>Update role</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('successMessage')!=null}">
    <h3 class="green"><c:out value="${sessionScope.get('login')} ${requestScope.get('successMessage')} ${sessionScope.get('role')}"/></h3>
    <c:remove var="successMessage" scope="request"/>
</c:if>
<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>


<div class="container">
    <h2>${enterLogin}</h2>

    <form action="DispatcherServlet" method="post">
        <input type="hidden" name="command" value="update-role"/>

        <label for="login">${login}<br></label>
        <input id="login" class="form-control" type="text" name="login" required/>
        <br>
        <br>
        <h2>${chooseRole}</h2>
        <label><input type="radio" name="role" value="admin" checked/>${admin}</label>
        <label><input type="radio" name="role" value="tenant" checked/>${tenant}</label>
        <label><input type="radio" name="role" value="employee" checked/>${employee}</label>
        <label><input type="radio" name="role" value="dispatcher" checked/>${dispatcher}</label>


        <button type="submit" class="btn btn-primary">${submit}</button>
    </form>

</div>
<c:import url="../menu/footer.jsp"/>

</body>
</html>
