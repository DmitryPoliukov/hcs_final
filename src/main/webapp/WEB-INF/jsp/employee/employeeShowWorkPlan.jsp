<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.dispatcherShowWorksPlan.enterDate" var="enterDate"/>
<fmt:message bundle="${locale}" key="locale.dispatcherShowWorksPlan.submit" var="submit"/>
<fmt:message bundle="${locale}" key="locale.dispatcherShowWorksPlan.noRequests" var="noRequests"/>
<fmt:message bundle="${locale}" key="locale.employeeShowWorkPlan.show" var="show"/>
<fmt:message bundle="${locale}" key="locale.common.workRequestId" var="requestId"/>
<fmt:message bundle="${locale}" key="locale.common.fillingDate" var="fillingDate"/>
<fmt:message bundle="${locale}" key="locale.common.plannedDate" var="plannedDate"/>
<fmt:message bundle="${locale}" key="locale.common.subInfo" var="subInfo"/>
<fmt:message bundle="${locale}" key="locale.common.tenantName" var="tenantName"/>
<fmt:message bundle="${locale}" key="locale.common.tenantPhone" var="tenantPhone"/>
<fmt:message bundle="${locale}" key="locale.common.tenantCity" var="tenantCity"/>
<fmt:message bundle="${locale}" key="locale.common.tenantAddress" var="tenantAddress"/>
<fmt:message bundle="${locale}" key="locale.employeeShowWorkPlan.date" var="date"/>

<!DOCTYPE html>
<html>
<head>
    <title>Show work plan</title>
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

<c:if test="${requestScope.get('workRequestList') == null}">
<div class="container">
    <h2>${enterDate}</h2>

    <form action="DispatcherServlet" method="post">
        <input type="hidden" name="command" value="show-work-plan"/>

        <label for="plannedDate">${date}<br></label>
        <input id="plannedDate" class="form-control" type="text" placeholder="DD.MM.YYYY" name="plannedDate" required/>
        <br>
        <button type="submit" class="btn btn-primary">${submit}</button>
    </form>

</div>
</c:if>

<c:if test="${requestScope.get('workRequestList').size() == 0}">
    <h2>${noRequests}</h2>
</c:if>

<c:if test="${requestScope.get('workRequestList').size() > 0}">
<div class="col-sm-12 text-center mainContent">
    <h1>${show}</h1>


    <br>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>${requestId}</th>
            <th>${fillingDate}</th>
            <th>${plannedDate}</th>
            <th>${subInfo}</th>
            <th>${tenantName}</th>
            <th>${tenantPhone}</th>
            <th>${tenantCity}</th>
            <th>${tenantAddress}</th>

        </tr>
        </thead>
        <tbody>

        <c:forEach begin="0" end="${requestScope.workRequestList.size()-1}" var="i">
        <tr>
            <td>${requestScope.workRequestList.get(i).requestID}</td>
            <td>${requestScope.workRequestList.get(i).fillingDate}</td>
            <td>${requestScope.workRequestList.get(i).plannedDate}</td>
            <td>${requestScope.firstSubquery.workType} - ${requestScope.firstSubquery.information}</td>
            <td>${requestScope.tenantList.get(i).name} ${requestScope.tenantList.get(i).surname}</td>
            <td>${requestScope.tenantList.get(i).phone}</td>
            <td>${requestScope.tenantInfoList.get(i).get(0)}</td>
            <td>${requestScope.tenantInfoList.get(i).get(1)}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:import url="../menu/footer.jsp"/>

</body>
</html>