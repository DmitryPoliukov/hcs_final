
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
    <title>Add Request to work plan</title>
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
        <h1>Planned date  ${requestScope.workRequest.plannedDate}</h1>

        <form action="DispatcherServlet" method="post">
            <input type="hidden" name="command" value="add-request-to-work-plan"/>
        <br>
        <table class="table table-hover">

            <thead>
            <tr>
                <th>Subqueries amount of work in hours</th>
                <th>Subqueries information</th>
                <th>Subqueries work type</th>
                <th>Assign</th>

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
            <button type="submit" class="btn btn-primary">Assign</button>
        </form>
    </div>
</div>






<c:import url="../menu/footer.jsp"/>
</body>
</html>
