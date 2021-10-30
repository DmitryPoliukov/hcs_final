<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.showAllTenantRequests.yourRequests" var="yourRequests"/>
<fmt:message bundle="${locale}" key="locale.showAllTenantRequests.cancelRequest" var="cancelRequest"/>
<fmt:message bundle="${locale}" key="locale.common.fillingDate" var="fillingDate"/>
<fmt:message bundle="${locale}" key="locale.common.plannedDate" var="plannedDate"/>
<fmt:message bundle="${locale}" key="locale.common.subWorkType" var="subWorkType"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.status" var="status"/>
<fmt:message bundle="${locale}" key="locale.common.cancel" var="cancel"/>
<fmt:message bundle="${locale}" key="locale.common.next" var="next"/>
<fmt:message bundle="${locale}" key="locale.common.previous" var="previous"/>


<!DOCTYPE html>
<html>
<head>
    <title>Show all requests</title>
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

<c:if test="${requestScope.get('deleteSuccessMessage')!=null}">
    <h3 class="green"><c:out value="${requestScope.get('deleteSuccessMessage')}"/></h3>
    <c:remove var="deleteSuccessMessage" scope="request"/>
</c:if>
<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<div class="container-fluid text-center wrapper">
    <br class="row content">
    <div class="col-sm-12 text-center mainContent">
        <h1>${yourRequests}</h1>
    <br>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>${fillingDate}</th>
            <th>${plannedDate}</th>
            <th>${status}</th>
            <th>${subWorkType}</th>
            <th>${cancelRequest}</th>

        </tr>
        </thead>
        <tbody>

        <c:forEach var="request" items="${requestScope.workRequestList}">
            <tr>
                <td>${request.fillingDate}</td>
                <td>${request.plannedDate}</td>
                <td>${request.requestStatus}</td>
                <td><c:forEach var="subquery" items="${request.subqueryList}">
                    |   ${subquery.workType}   |
                </c:forEach>
                </td>
                <td> <a href="DispatcherServlet?command=update-request-status-tenant&delete=${request.requestID}" class="btn btn-info" role="button"> ${cancel} </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>

    <style type="text/css">
        div.pagination {
            margin-top: 10px;
            text-align: center;
        }
        .pagination a {
            color: #3b5998;
            text-decoration: none;
            border: 1px #c2d1df solid;
            padding: 2px 5px;
            margin-right: 5px;
        }
        .pagination a:hover {
            color: #3b5998;
            text-decoration: none;
            border: 1px #3b5998 solid;
        }
        a.current {
            background: #c2d6ed;
        }
    </style>
    <div class="pagination">
        <%--For displaying Previous link except for the 1st page --%>
        <ul class="pagination">
            <c:if test="${requestScope.currentPage > 1}">
                <li>
                    <a href="DispatcherServlet?command=show-all-tenant-requests&page=${requestScope.currentPage - 1}"> ${previous} </a>
                </li>

            </c:if>

            <c:if test="${requestScope.noOfPages>1}">
                <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.currentPage eq i}">
                            <li class="active"><a href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="DispatcherServlet?command=show-all-tenant-requests&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
            <%--For displaying Next link --%>
            <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                <li>
                    <a href="DispatcherServlet?command=show-all-tenant-requests&page=${requestScope.currentPage + 1}">${next}</a>
                </li>
            </c:if>


        </ul>
    </div>
</div>
</div>

<c:import url="../menu/footer.jsp"/>
</body>
</html>
