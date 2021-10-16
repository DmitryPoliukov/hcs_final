<%--
  Created by IntelliJ IDEA.
  User: radof
  Date: 15.10.2021
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

</head>
<body>

<c:import url="../menu/menu.jsp"/>

<div class="container-fluid text-center wrapper">
    <br class="row content">
    <div class="col-sm-12 text-center mainContent">
        <h1>Your requests</h1>


    <br>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Filling date</th>
            <th>Planned date</th>
            <th>Status</th>
            <th>Subqueries work type</th>

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
                    <a href="DispatcherServlet?command=show-all-tenant-requests&page=${requestScope.currentPage - 1}"> previous </a>
                </li>

            </c:if>
            <%--For displaying Page numbers.
                    The when condition does not display a link for the current page--%>
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
                    <a href="DispatcherServlet?command=show-all-tenant-requests&page=${requestScope.currentPage + 1}">next</a>
                </li>
            </c:if>

        </ul>
    </div>
</div>
</div>
</div>




<c:import url="../menu/footer.jsp"/>
</body>
</html>
