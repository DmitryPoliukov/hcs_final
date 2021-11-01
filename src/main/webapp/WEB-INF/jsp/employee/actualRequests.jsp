<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.common.all" var="all"/>
<fmt:message bundle="${locale}" key="locale.common.paintingWorks" var="paintingWorks"/>
<fmt:message bundle="${locale}" key="locale.common.concreteWorks" var="concreteWorks"/>
<fmt:message bundle="${locale}" key="locale.common.next" var="next"/>
<fmt:message bundle="${locale}" key="locale.common.previous" var="previous"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.newWorkRequests" var="newWorkRequests"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.workType" var="workType"/>
<fmt:message bundle="${locale}" key="locale.common.fillingDate" var="fillingDate"/>
<fmt:message bundle="${locale}" key="locale.common.plannedDate" var="plannedDate"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.status" var="status"/>
<fmt:message bundle="${locale}" key="locale.common.subWorkType" var="subWorkType"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.amountOfWorkInHours" var="amountOfWorkInHours"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.information" var="information"/>
<fmt:message bundle="${locale}" key="locale.common.tenantCity" var="tenantCity"/>
<fmt:message bundle="${locale}" key="locale.common.tenantAddress" var="tenantAddress"/>
<fmt:message bundle="${locale}" key="locale.actualRequests.assign" var="assign"/>
<fmt:message bundle="${locale}" key="locale.common.improvementOfTheTerritory" var="improvementOfTheTerritory"/>
<fmt:message bundle="${locale}" key="locale.common.plumbingWork" var="plumbingWork"/>
<fmt:message bundle="${locale}" key="locale.common.electricInstallationWork" var="electricInstallationWork"/>
<fmt:message bundle="${locale}" key="locale.common.floorWork" var="floorWork"/>
<fmt:message bundle="${locale}" key="locale.common.plasteringWork" var="plasteringWork"/>
<fmt:message bundle="${locale}" key="locale.common.carpenterServices" var="carpenterServices"/>



<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <title>New requests</title>
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
    <div class="col-sm-12 text-left mainContent">
        <h1>${newWorkRequests}</h1>

        <div class="selectboxes">
            <div class="sortAwaits" style="width: 391px; float: left;margin: 0">
                <span>${workType} </span>
                <label>
                    <select onchange="MakeSort(this);">
                        <option value="DispatcherServlet?command=actual-requests-all-types">-</option>
                        <option value="DispatcherServlet?command=actual-requests-all-types">${all}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Малярные работы">${paintingWorks}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Бетонные работы">${concreteWorks}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Благоустройство">${improvementOfTheTerritory}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Сантехнические работы">${plumbingWork}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Электромонтажные работы">${electricInstallationWork}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Напольные работы">${floorWork}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Штукатурные работы">${plasteringWork}</option>
                        <option value="DispatcherServlet?command=actual-requests-employee&type=Услуги столяра">${carpenterServices}</option>
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
<c:if test='${requestScope.actualRequests != null}'>
    <table class="table table-hover">

        <thead>
        <tr>
            <th>${fillingDate}</th>
            <th>${plannedDate}</th>
            <th>${status}</th>
            <th>${subWorkType}</th>
            <th>${amountOfWorkInHours}</th>
            <th>${information}</th>
            <th>${tenantCity}</th>
            <th>${tenantAddress}</th>

<c:if test='${sessionScope.get("user").role eq "dispatcher"}'>
            <th>${assign}</th>
</c:if>

        </tr>
        </thead>
        <tbody>
        <c:forEach begin="0" end="${requestScope.actualRequests.size()-1}" var="i">

            <tr>
                <td>${requestScope.actualRequests.get(i).fillingDate}</td>
                <td>${requestScope.actualRequests.get(i).plannedDate}</td>
                <td>${requestScope.actualRequests.get(i).requestStatus}</td>

                <td> <c:forEach var="subquery" items="${requestScope.actualRequests.get(i).subqueryList}">
                        ${subquery.workType}<br>
                </c:forEach></td>

                <td><c:forEach var="subquery" items="${requestScope.actualRequests.get(i).subqueryList}">
                ${subquery.amountOfWorkInHours}<br>
                </c:forEach></td>

                <td><c:forEach var="subquery" items="${requestScope.actualRequests.get(i).subqueryList}">
                    ${subquery.information}<br>
                </c:forEach></td>

                <td>${requestScope.tenantInfoList.get(i).get(0)}</td>
                <td>${requestScope.tenantInfoList.get(i).get(1)}</td>


                <c:if test='${sessionScope.get("user").role eq "dispatcher"}'>
                <td><a href="DispatcherServlet?command=go-to-add-request-to-work-plan&requestId=${requestScope.actualRequests.get(i).requestID}" class="btn btn-info" role="button">${assign}</a></td>
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
                    <a href="DispatcherServlet?command=${param.command}&type=${param.type}&page=${requestScope.currentPage - 1}">${previous}</a>
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
                    <a href="DispatcherServlet?command=${param.command}&type=${param.type}&page=${requestScope.currentPage + 1}">${next}</a>
                </li>
            </c:if>

        </ul>
    </div>
</c:if>
</div>
<c:import url="../menu/footer.jsp"/>
</body>
</html>
