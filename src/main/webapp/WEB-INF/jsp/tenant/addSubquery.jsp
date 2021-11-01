<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.addSubquery.enterSubInfo" var="enterSubInfo"/>
<fmt:message bundle="${locale}" key="locale.addSubquery.addAnotherSubquery" var="addAnotherSubquery"/>
<fmt:message bundle="${locale}" key="locale.common.submit" var="submit"/>
<fmt:message bundle="${locale}" key="locale.addSubquery.showMyRequests" var="showMyRequests"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.amountOfWorkInHours" var="amountOfWork"/>
<fmt:message bundle="${locale}" key="locale.common.paintingWorks" var="paintingWorks"/>
<fmt:message bundle="${locale}" key="locale.common.concreteWorks" var="concreteWorks"/>
<fmt:message bundle="${locale}" key="locale.common.otherInformation" var="otherInformation"/>

<fmt:message bundle="${locale}" key="locale.common.improvementOfTheTerritory" var="improvementOfTheTerritory"/>
<fmt:message bundle="${locale}" key="locale.common.plumbingWork" var="plumbingWork"/>
<fmt:message bundle="${locale}" key="locale.common.electricInstallationWork" var="electricInstallationWork"/>
<fmt:message bundle="${locale}" key="locale.common.floorWork" var="floorWork"/>
<fmt:message bundle="${locale}" key="locale.common.plasteringWork" var="plasteringWork"/>
<fmt:message bundle="${locale}" key="locale.common.carpenterServices" var="carpenterServices"/>

<!DOCTYPE html>
<html>
<head>
    <title>Add subquery</title>
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

<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<c:if test="${sessionScope.get('successMessageSubquery')==null}">
<div class="container">
    <h2>${enterSubInfo}</h2>

    <form action="DispatcherServlet" method="post">
        <input type="hidden" name="command" value="add-subquery"/>

        <input type="hidden" name="workRequestId" value="${sessionScope.get('workRequest').requestID}"/>

        <label for="amount">${amountOfWork}<br></label>
        <input id="amount" class="form-control" type="text" name="amount" required/>
        <br>
        <label for="information">${otherInformation}<br></label>
        <input id="information" class="form-control" type="text" name="information" required/>
        <br>

        <label for="workType"></label>
        <select name="workType" id="workType">
            <option value="Малярные работы">${paintingWorks}</option>
            <option value="Бетонные работы">${concreteWorks}</option>
            <option value="Благоустройство">${improvementOfTheTerritory}</option>
            <option value="Сантехнические работы">${plumbingWork}</option>
            <option value="Электромонтажные работы">${electricInstallationWork}</option>
            <option value="Напольные работы">${floorWork}</option>
            <option value="Штукатурные работы">${plasteringWork}</option>
            <option value="Услуги столяра">${carpenterServices}</option>

        </select>
        <br>
        <br>
        <button type="submit" class="btn btn-primary">${submit}</button>
    </form>
    </c:if>


<c:if test="${sessionScope.get('successMessageSubquery')!=null}">
    <h3 class="green"><c:out value="${sessionScope.get('successMessageSubquery')}"/></h3>
    <c:remove var="successMessageSubquery" scope="session"/>
    <br><br>

    <a href="DispatcherServlet?command=go-to-add-subquery" class="btn btn-info" role="button">${addAnotherSubquery}</a>
    <a href="DispatcherServlet?command=show-all-tenant-requests" class="btn btn-info" role="button">${showMyRequests}</a>
</c:if>

</div>
    <c:import url="../menu/footer.jsp"/>

</body>
</html>









