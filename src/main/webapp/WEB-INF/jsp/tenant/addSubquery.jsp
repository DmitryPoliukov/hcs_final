<%--
  Created by IntelliJ IDEA.
  User: radof
  Date: 14.10.2021
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<c:import url="../menu/menu.jsp"/>

<c:if test="${requestScope.get('successMessage')!=null}">
    <h3 class="green"><c:out value="${requestScope.get('successMessage')}"/></h3>
    <c:remove var="successMessage" scope="request"/>
</c:if>

<c:if test="${requestScope.get('successMessageSubquery')==null}">
<div class="container">
    <h2>Please, enter subquerry information</h2>

    <form action="DispatcherServlet" method="post">
        <input type="hidden" name="command" value="add-subquery"/>

        <input type="hidden" name="workRequestId" value="${sessionScope.get('workRequest').requestID}"/>

        <label for="amount">amount of work in hours<br></label>
        <input id="amount" class="form-control" type="text" name="amount" required/>
        <br/>
        <label for="information">other subquery information<br></label>
        <input id="information" class="form-control" type="text" name="information" required/>
        <br/>

        <select name="workType" id="workType">
            <option value="Малярные работы">Малярные работы</option>
            <option value="Бетонные работы">Бетонные работы</option>
            <option value="Благоустройство">Благоустройство</option>

        </select>
        <br>
        <button type="submit" class="btn btn-primary">submit</button>
    </form>
    </c:if>


<c:if test="${requestScope.get('successMessageSubquery')!=null}">
    <h3 class="green"><c:out value="${requestScope.get('successMessageSubquery')}"/></h3>
    <c:remove var="successMessageSubquery" scope="request"/>
    <br><br>

    <a href="DispatcherServlet?command=go-to-add-subquery" class="btn btn-info" role="button"> Add another subquery </a>
    <a href="DispatcherServlet?command=show-all-tenant-requests" class="btn btn-info" role="button"> Show my requests </a>
</c:if>


    </a>
</div>
    <c:import url="../menu/footer.jsp"/>

</body>
</html>














</div>
<div class="footer">
    <jsp:include page="../menu/footer.jsp"/>
</div>


</body>
</html>