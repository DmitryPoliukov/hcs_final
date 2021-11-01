
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="user" class="by.epamtc.poliukov.entity.User" scope="request"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set scope="session" var="previousQuery" value="index.jsp"/>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.addRequestToWorkPlan.subAmount" var="subAmount"/>
<fmt:message bundle="${locale}" key="locale.addRequestToWorkPlan.subInfo" var="subInfo"/>
<fmt:message bundle="${locale}" key="locale.addRequestToWorkPlan.subWorkType" var="subWorkType"/>
<fmt:message bundle="${locale}" key="locale.addRequestToWorkPlan.assign" var="assign"/>
<fmt:message bundle="${locale}" key="locale.addRequestToWorkPlan.plannedDate" var="plannedDate"/>

<!DOCTYPE html>
<html>
<head>
    <title>Add request to work plan</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<c:import url="../menu/menu.jsp"/>


<c:if test="${requestScope.get('successMessage')!=null}">
    <h3 class="green"><c:out value="${requestScope.get('successMessage')}"/></h3>
    <c:remove var="successMessage" scope="request"/>
</c:if>
<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<div class="container-fluid text-center wrapper">
    <br class="row content">
    <div class="col-sm-8 text-center mainContent">
        <h1>${plannedDate} ${requestScope.workRequest.plannedDate}</h1>

        <form action="DispatcherServlet" method="post">
            <input type="hidden" name="command" value="add-request-to-work-plan"/>
        <br>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>${subAmount}</th>
                <th>${subInfo}</th>
                <th>${subWorkType}</th>
                <th>${assign}</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="subquery" items="${requestScope.workRequest.subqueryList}">
                <tr>
                    <td>${subquery.amountOfWorkInHours}</td>
                    <td>${subquery.information}</td>
                    <td>${subquery.workType}</td>
                    <td>

                        <input type="hidden" name="requestId" value="${requestScope.workRequest.requestID}"/>
                        <input type="hidden" name="subqueryId" value="${subquery.subId}"/>

                        <label for="employeeId"></label>
                        <select name="employeeId" id="employeeId">
                            <c:forEach var="employee" items="${requestScope.allEmployees}">
                                <option value="${employee.userId}">${employee.name} ${employee.surname} ${employee.employeeWorkTypeName}</option>
                            </c:forEach>
                        </select>
                        <br>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br>
            <button type="submit" class="btn btn-primary">${assign}</button>
        </form>
    </div>
</div>

<c:import url="../menu/footer.jsp"/>
</body>
</html>
