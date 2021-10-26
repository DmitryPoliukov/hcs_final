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
    <title>show work plan</title>
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

<c:if test="${requestScope.get('successMessage')!=null}">
    <h3 class="green"><c:out value="${requestScope.get('successMessage')}"/></h3>
    <c:remove var="successMessage" scope="request"/>
</c:if>
<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('workRequestList') == null}">
    <div class="container">
        <h2>Please, enter the date</h2>

        <form action="DispatcherServlet" method="post">
            <input type="hidden" name="command" value="dispatcher-work-plan"/>

            <input type="hidden" name="employeeId" value="${param.employeeId}"/>
            <label for="plannedDate">date<br></label>
            <input id="plannedDate" class="form-control" type="text" name="plannedDate" required/>
            <br/>
            <br>
            <br>
            <br>

            <button type="submit" class="btn btn-primary">Show work plan</button>
        </form>

    </div>
</c:if>

<c:if test="${requestScope.get('workRequestList').size() == 0}">
    <h2>No requests for this date</h2>
</c:if>

<c:if test="${requestScope.get('workRequestList').size() > 0}">
<div class="col-sm-12 text-center mainContent">
    <h1>Work plan for ${requestScope.employee.name} ${requestScope.employee.surname} </h1>


    <br>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Work request id</th>
            <th>Filling date</th>
            <th>Planned date</th>
            <th>Subqueries information</th>
            <th>Tenant's name</th>
            <th>Tenant's phone</th>
            <th>Tenant's city</th>
            <th>Tenant's address</th>
            <th>Update work request status</th>

        </tr>
        </thead>
        <tbody>

        <c:forEach begin="0" end="${requestScope.workRequestList.size()-1}" var="i">
            <tr>
                <td>${requestScope.workRequestList.get(i).requestID}</td>
                <td>${requestScope.workRequestList.get(i).fillingDate}</td>
                <td>${requestScope.workRequestList.get(i).plannedDate}</td>
                <td><c:forEach var="subquery" items="${requestScope.workRequestList.get(i).subqueryList}">
                    ${subquery.workType} -- ${subquery.information} <br>
                </c:forEach>
                </td>
                <td>${requestScope.tenantList.get(i).name} ${requestScope.tenantList.get(i).surname}</td>
                <td>${requestScope.tenantList.get(i).phone}</td>
                <td>${requestScope.tenantInfoList.get(i).get(0)}</td>
                <td>${requestScope.tenantInfoList.get(i).get(1)}</td>
                <td><a href="DispatcherServlet?command=dispatcher-close-work-request&requestId=${requestScope.workRequestList.get(i).requestID}" class="btn btn-success" role="button">Close work request</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    </c:if>


    <c:import url="../menu/footer.jsp"/>

</body>
</html>