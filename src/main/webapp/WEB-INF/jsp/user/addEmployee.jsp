<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.addEmployee.enterInfo" var="enterInfo"/>
<fmt:message bundle="${locale}" key="locale.addEmployee.valuePersonHour" var="valuePersonHour"/>
<fmt:message bundle="${locale}" key="locale.addEmployee.enterSpecialization" var="enterSpecialization"/>
<fmt:message bundle="${locale}" key="locale.addEmployee.showNewWorkRequests" var="showNewWorkRequests"/>
<fmt:message bundle="${locale}" key="locale.addEmployee.home" var="home"/>
<fmt:message bundle="${locale}" key="locale.common.paintingWorks" var="paintingWorks"/>
<fmt:message bundle="${locale}" key="locale.common.concreteWorks" var="concreteWorks"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.information" var="information"/>
<fmt:message bundle="${locale}" key="locale.common.submit" var="submit"/>


<!DOCTYPE html>
<html>
<head>
    <TITLE>Add employee</TITLE>
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

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<c:if test="${requestScope.get('successMessage')==null}">
<div class="container">
    <h2>${enterInfo}</h2>
    <form action="DispatcherServlet" method="post">
        <input type="hidden" name="command" value="add-employee"/>

        <label for="valuePersonHour">${valuePersonHour}<br></label>
        <input id="valuePersonHour" class="form-control" type="text" name="valuePersonHour" required/>
        <br/>
        <label for="information">${information}<br></label>
        <input id="information" class="form-control" type="text" name="information" required/>
        <br/>
<h3>${enterSpecialization}</h3>
        <div class="form-check">
            <label class="form-check-label" for="check1">
                <input type="checkbox" class="form-check-input" id="check1" name="workType" value="Малярные работы">${paintingWorks}
            </label>
        </div>
        <div class="form-check">
            <label class="form-check-label" for="check2">
                <input type="checkbox" class="form-check-input" id="check2" name="workType" value="Бетонные работы">${concreteWorks}
            </label>
        </div>
        <div class="form-check">
            <label class="form-check-label" for="check3">
                <input type="checkbox" class="form-check-input" id="check3" name="workType" value="Благоустройство">Благоустройство
            </label>
        </div>

        <button type="submit" class="btn btn-primary">${submit}</button>
    </form>
</div>
</c:if>
    <c:if test="${requestScope.get('successMessage')!=null}">
        <h3 class="green"><c:out value="${requestScope.get('successMessage')}"/></h3>
        <c:remove var="successMessage" scope="request"/>

        <a href="DispatcherServlet?command=go-to-actual-requests-employee" class="btn btn-info" role="button">${showNewWorkRequests}</a>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-info" role="button">${home}</a>

    </c:if>

<div class="footer">
    <jsp:include page="../menu/footer.jsp"/>
</div>

</body>
</html>