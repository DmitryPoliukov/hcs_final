<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.addRequest.enterRequestInfo" var="enterRequestInfo"/>
<fmt:message bundle="${locale}" key="locale.common.submit" var="submit"/>
<fmt:message bundle="${locale}" key="locale.common.plannedDate" var="plannedDate"/>


<!DOCTYPE html>
<html>
<head>
    <title>Add work request</title>
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

</head>
<body>
<c:if test="${requestScope.get('successMessage')!=null}">
    <h3 class="green"><c:out value="${requestScope.get('successMessage')}"/></h3>
    <c:remove var="successMessage" scope="request"/>
</c:if>

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<div class="container">
    <h2>${enterRequestInfo}</h2>

    <c:if test="${requestScope.get('successMessage')==null}">
        <form action="DispatcherServlet" method="post">
            <input type="hidden" name="command" value="add-work-request"/>

            <label for="plannedDate">${plannedDate}<br></label>
            <input id="plannedDate" class="form-control" type="text" placeholder="DD.MM.YYYY" name="plannedDate" required/>
            <br/>

            <button type="submit" class="btn btn-primary">${submit}</button>
        </form>
    </c:if>

</div>
<c:import url="../menu/footer.jsp"/>

</body>
</html>
