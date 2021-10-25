<%--
  Created by IntelliJ IDEA.
  User: radof
  Date: 18.10.2021
  Time: 01:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <title>actual requests</title>
</head>
<body onload="active()">

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<script language="JavaScript">
    function active() {
        document.getElementById("employees-page").className = "active";
    }
</script>
<div class="container-fluid text-center wrapper">
    <br class="row content">
    <div class="col-sm-12 text-left mainContent">
        <h1>Actual work requests</h1>

        <div class="selectboxes">
            <div class="sortAwaits" style="width: 391px; float: left;margin: 0">
                <span>Work type </span>
                <label>
                    <select onchange="MakeSort(this);">
                        <option value="DispatcherServlet?command=actual-requests-all-types">-</option>
                        <option value="DispatcherServlet?command=actual-requests-all-types">all</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Малярные работы">Малярные работы</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Бетонные работы">Бетонные работы</option>
                    </select>
                </label>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <script type="text/javascript">
        function MakeSort(element) {
            var selected = $('option:selected', element),
                href = selected.val();
            if (/*selected.text() === '-' || */!href) {
                return false;
            }
            document.location = href;
        }
    </script>
    <br>
    <table class="table table-hover">

        <thead>
        <tr>
            <th>Filling date</th>
            <th>Planned date</th>
            <th>Status</th>
            <th>Subqueries work type</th>
            <th>Amount of work in hours</th>
            <th>Information</th>
<c:if test='${sessionScope.get("user").role eq "dispatcher"}'>
            <th>Assign an employee</th>
</c:if>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="request" items="${requestScope.actualRequests}">
            <tr>
                <td>${request.fillingDate}</td>
                <td>${request.plannedDate}</td>
                <td>${request.requestStatus}</td>
                <td>${request.subqueryList[0].workType}</td>
                <td>${request.subqueryList[0].amountOfWorkInHours}</td>
                <td>${request.subqueryList[0].information}</td>
                <c:if test='${sessionScope.get("user").role eq "dispatcher"}'>
                <td><a href="DispatcherServlet?command=go-to-add-request-to-work-plan&requestId=${request.requestID}" class="btn btn-info" role="button"> Assign </a></td>
                </c:if>
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
                    <a href="DispatcherServlet?command=${param.command}&type=${param.type}&page=${requestScope.currentPage - 1}"> previous </a>
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
                                <a href="DispatcherServlet?command=${param.command}&type=${param.type}&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
            <%--For displaying Next link --%>
            <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                <li>
                    <a href="DispatcherServlet?command=${param.command}&type=${param.type}&page=${requestScope.currentPage + 1}">next</a>
                </li>
            </c:if>

        </ul>
    </div>
</div>
<c:import url="../menu/footer.jsp"/>
</body>
</html>