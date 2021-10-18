
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set scope="session" var="previousQuery" value="index.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Sign In</title>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial scale=1">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Bootstrap JS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>



</head>
<body onload="active()">
<c:import url="menu/menu.jsp"/>

<script language="javascript">
    function active() {
        document.getElementById("index-page").className = "active";
    }
</script>
<div class="container-fluid text-center wrapper">
    <div class="row content">

        <div class="col-sm-6 text-left mainContent">

            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <a href="${pageContext.request.contextPath}/index.jsp">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-hidden="true">&times;  </button>
                        </a>
                        <h4 class="modal-title">HCS</h4>
                    </div>
                    <c:if test="${requestScope.get('errorMessage')!=null}">
                        <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
                        <c:remove var="errorMessage" scope="request"/>
                    </c:if>
                    <div class="modal-body text-center">
                        <form name="loginForm" class="form-horizontal" method="post" action="DispatcherServlet"
                              onsubmit="return validateForm();">
                            <input type="hidden" name="command" value="login"/>
                            <div class="form-group">
                                <label for="username" class="col-sm-5 control-label">username </label>
                                <span id="unameDemo" class="red"></span>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="username" placeholder="username"
                                           name="username" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password3" class="col-sm-5 control-label">password</label>
                                <span id="pswDemo" class="red"></span>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" id="password3" placeholder="password"
                                           name="pass" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-6">
                                    <button type="submit" class="btn btn-primary">Sign In</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <a href="${pageContext.request.contextPath}/index.jsp">
                            <button type="button" class="btn btn-default" data-dismiss="modal">cancel</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script language="javascript">
    function validateForm() {
        let uname, psw;
        let unameText, pswText;
        let result = true;
        uname = document.forms["loginForm"]["username"].value;
        psw = document.forms["loginForm"]["pass"].value;
        const unamePattern = /[a-zA-Z_0-9]{3,16}/;
        if (!unamePattern.test(uname)) {
            unameText = "Username should contain only latin symbols, digits and _";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length < 3) {
            unameText = "Username should be at least 3 symbols.";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length > 16) {
            unameText = "Username should be less then 17 symbols.";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("unameDemo").innerHTML = unameText;
        }
        const passPattern = /[a-zA-Z0-9_]{3,32}/;
        if (psw.length < 6) {
            pswText = "Password should be at least 4 symbols";
            document.getElementById("pswDemo").innerHTML = pswText;
            result = false;
        } else if (!passPattern.test(psw)) {
            pswText = "Password should contain only latin symbols, digits and _";
            document.getElementById("pswDemo").innerHTML = pswText;
            result = false;
        } else if (psw.length > 32) {
            pswText = "Password should be less then 32 symbols";
            document.getElementById("pswDemo").innerHTML = pswText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("pswDemo").innerHTML = unameText;
        }
        return result;
    }
</script>
</body>
</html>