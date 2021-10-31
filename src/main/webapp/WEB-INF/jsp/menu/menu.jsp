<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.menu.home" var="home"/>
    <fmt:message bundle="${locale}" key="locale.menu.makeWorkRequest" var="makeRequest"/>
    <fmt:message bundle="${locale}" key="locale.menu.becomeEmployee" var="becomeEmployee"/>
    <fmt:message bundle="${locale}" key="locale.menu.updateRoles" var="updateRoles"/>
    <fmt:message bundle="${locale}" key="locale.menu.newRequests" var="newRequests"/>
    <fmt:message bundle="${locale}" key="locale.menu.workPlan" var="workPlan"/>
    <fmt:message bundle="${locale}" key="locale.menu.showWorkPlan" var="showWorkPlan"/>
    <fmt:message bundle="${locale}" key="locale.menu.assignEmployeeToWorkRequests" var="assign"/>
    <fmt:message bundle="${locale}" key="locale.menu.ourCommand" var="ourCommand"/>
    <fmt:message bundle="${locale}" key="locale.menu.language" var="language"/>
    <fmt:message bundle="${locale}" key="locale.menu.signUp" var="signUp"/>
    <fmt:message bundle="${locale}" key="locale.menu.login1" var="login1"/>
    <fmt:message bundle="${locale}" key="locale.updateRole.admin" var="admin"/>
    <fmt:message bundle="${locale}" key="locale.updateRole.tenant" var="tenant"/>
    <fmt:message bundle="${locale}" key="locale.updateRole.employee" var="employee"/>
    <fmt:message bundle="${locale}" key="locale.updateRole.dispatcher" var="dispatcher"/>
    <fmt:message bundle="${locale}" key="locale.common.password" var="password"/>
    <fmt:message bundle="${locale}" key="locale.common.cancel" var="cancel"/>
    <fmt:message bundle="${locale}" key="locale.common.username" var="username"/>
    <fmt:message bundle="${locale}" key="locale.common.register" var="register"/>
    <fmt:message bundle="${locale}" key="locale.common.repeatPassword" var="repeatPassword"/>
    <fmt:message bundle="${locale}" key="locale.menu.hello" var="hello"/>
    <fmt:message bundle="${locale}" key="locale.menu.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="locale.menu.signIn" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.common.username" var="username"/>
    <fmt:message bundle="${locale}" key="locale.common.name" var="name"/>
    <fmt:message bundle="${locale}" key="locale.common.secondName" var="secondName"/>
    <fmt:message bundle="${locale}" key="locale.common.email" var="email"/>
    <fmt:message bundle="${locale}" key="locale.common.phone" var="phone"/>
    <fmt:message bundle="${locale}" key="locale.common.surname" var="surname"/>
    <fmt:message bundle="${locale}" key="locale.common.user" var="user"/>
    <fmt:message bundle="${locale}" key="locale.menu.eUsername" var="eUsername"/>
    <fmt:message bundle="${locale}" key="locale.menu.eNicknameMin" var="eNicknameMin"/>
    <fmt:message bundle="${locale}" key="locale.menu.eUsernameMax" var="eUsernameMax"/>
    <fmt:message bundle="${locale}" key="locale.menu.ePasswordMin" var="ePasswordMin"/>
    <fmt:message bundle="${locale}" key="locale.menu.ePassword" var="ePassword"/>
    <fmt:message bundle="${locale}" key="locale.menu.ePasswordMax" var="ePasswordMax"/>
    <fmt:message bundle="${locale}" key="locale.menu.eUsernameMin" var="eUsernameMin"/>
    <fmt:message bundle="${locale}" key="locale.menu.eMail" var="eMail"/>
    <fmt:message bundle="${locale}" key="locale.menu.ePassRepeat" var="ePassRepeat"/>



    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Poliukov_fin</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">${home}</a>
    <div class="collapse navbar-collapse" id="myNavbar">
    <ul class="nav navbar-nav navbar-right">

    <c:if test="${sessionScope.get('user') != null}">
            <c:if test='${sessionScope.get("user").role eq "tenant"}'>
                <li><a href="DispatcherServlet?command=go-to-add-work-request">${makeRequest}</a></li>
            </c:if>

            <c:if test='${sessionScope.get("user").role eq "user"}'>
                <li> <a href="DispatcherServlet?command=go-to-add-tenant">${makeRequest}</a> </li>
                <li> <a href="DispatcherServlet?command=go-to-add-employee">${becomeEmployee}</a></li>
            </c:if>

            <c:if test='${sessionScope.get("user").role eq "admin"}'>
                <li> <a href="DispatcherServlet?command=go-to-update-role">${updateRoles}</a></li>
            </c:if>
            <c:if test='${sessionScope.get("user").role eq "employee"}'>
                <li> <a href="DispatcherServlet?command=actual-requests-all-types">${newRequests}</a></li>
                <li> <a href="DispatcherServlet?command=go-to-show-work-plan">${workPlan}</a></li>
            </c:if>

            <c:if test='${sessionScope.get("user").role eq "dispatcher"}'>
                <li> <a href="DispatcherServlet?command=all-employee-work-plan">${showWorkPlan}</a></li>
                <li> <a href="DispatcherServlet?command=go-to-actual-requests-employee">${assign}</a></li>
            </c:if>
        </c:if>

    <li> <a href="DispatcherServlet?command=all-employees">${ourCommand}</a> </li>
    </ul>

    </div>
    <ul class="nav navbar-nav navbar-right pull-right">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#" >${language}</a>
        <ul class="dropdown-menu">
            <li><a href="DispatcherServlet?command=change-language&language=en">English</a></li>
            <li><a href="DispatcherServlet?command=change-language&language=ru">Русский</a></li>
        </ul>
    </li>
    <c:if test="${sessionScope.get('user') == null}">
        <li class="sign-up">
        <a data-toggle="modal" data-target="#register" href="#">
        <span class="glyphicon glyphicon-user"> </span>${signUp}</a></li>
        <li><a data-toggle="modal" data-target="#login" href="#">
        <span class="glyphicon glyphicon-log-in" style="width: 30px; float: left;margin: 0"></span>${login1}</a>
        </li>
    </c:if>
    <c:if test="${sessionScope.get('user') != null}">
        <li class="sign-up">
        <a href="DispatcherServlet?command=view-user&username=${sessionScope.get('user').login}">
        <span class="glyphicon glyphicon-user" ></span>${hello} ${sessionScope.get('user').login} |</a></li>
        <li><a href="DispatcherServlet?command=log-out">
        <span class="glyphicon glyphicon-log-out" style="width: 30px; float: left;margin: 0"></span>${logOut}</a>
        </li>
    </c:if>

    </ul>
    </nav>





<div id="login" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
    <div class="modal-content">
    <div class="modal-header">
    <h4 class="modal-title">${signIn}</h4>
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;  </button>
    </div>
    <div class="modal-body text-center">
    <form name="loginForm" class="form-horizontal" method="post" action="DispatcherServlet" onsubmit="return validateForm();">
    <input type="hidden" name="command" value="login"/>
    <span id="unameDemo" class="red"></span>
    <div class="form-group">
    <label for="username" class="col-4 control-label">${username}</label>
    <div class="col-11">
    <input type="text" class="form-control" id="username" placeholder="${username}"
    name="username" required>
    </div>
                    </div>
                    <div class="form-group">
                        <label for="password3" class="col-4 control-label">${password}</label>
                        <span id="pswDemo" class="red"></span>
                        <div class="col-11">
                            <input type="password" class="form-control" id="password3" placeholder="${password}"
                                   name="pass" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-offset-4 col-11">
                            <button type="submit" class="btn btn-primary">${signIn}</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">${cancel}</button>
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
            unameText = "${eUsername}";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length < 3) {
            unameText = "${eNicknameMin}";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length > 14) {
            unameText = "${eUsernameMax}";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("unameDemo").innerHTML = unameText;
        }
    const passPattern = /[a-zA-Z0-9_]{6,32}/;
    if (psw.length < 4) {
            pswText = "${ePasswordMin}";
            document.getElementById("pswDemo").innerHTML = pswText;
            result = false;
        } else if (!passPattern.test(psw)) {
            pswText = "${ePassword}";
            document.getElementById("pswDemo").innerHTML = pswText;
            result = false;
        } else if (psw.length > 32) {
            pswText = "${ePasswordMax}";
            document.getElementById("pswDemo").innerHTML = pswText;
            result = false;
        }
        return result;
    }
</script>

<div id="register" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">${register}</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;  </button>

            </div>
            <div class="modal-body text-center">
                <form name="registerForm" class="form-horizontal" method="post" action="DispatcherServlet"  onsubmit="return validateReg();">
                    <input type="hidden" name="command" value="register"/>
                    <div class="form-group">
                        <label for="inputUsername" class="col-4 control-label">${username}</label>
                        <span id="unameDemo2" class="red"></span>
                        <div class="col-11">
                            <input type="text" class="form-control" id="inputUsername" placeholder="${username}"
                                   name="login" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-10 control-label">${email}</label>
                        <span id="emailDemo2" class="red"></span>
                        <div class="col-11">
                            <input type="email" class="form-control" id="email" placeholder="${email}"
                                   name="email" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-4 control-label">${name}</label>
                        <div class="col-11">
                            <input type="text" class="form-control" id="name" placeholder="${name}"
                                   name="name" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="col-4 control-label">${surname}</label>
                        <div class="col-11">
                            <input type="text" class="form-control" id="surname" placeholder="${surname}"
                                   name="surname" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="second_name" class="col-4 control-label">${secondName}</label>
                        <div class="col-11">
                            <input type="text" class="form-control" id="second_name" placeholder="${secondName}"
                                   name="second_name" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="col-4 control-label">${phone}</label>
                        <div class="col-11">
                            <input type="text" class="form-control" id="phone" placeholder="${phone}"
                                   name="phone" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-5 control-label">${password}</label>
                        <span id="pswDemo2" class="red"></span>
                        <div class="col-11">
                            <input type="password" class="form-control" id="inputPassword3" placeholder="${password}"
                                   name="password" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword4" class="col-10 control-label">${repeatPassword}</label>
                        <div class="col-11">
                            <input type="password" class="form-control" id="inputPassword4" placeholder="${repeatPassword}"
                                   name="password2" required>
                        </div>
                    </div>
    <div class="modal-body text-left">
    <div class="form-check">
    <input class="form-check-input" type="radio" name="role_name" id="role_name1" value="tenant" checked>
    <label class="form-check-label" for="role_name1"> ${tenant} </label>
    </div>
    <div class="form-check">
    <input class="form-check-input" type="radio" name="role_name" id="role_name2" value="employee">
    <label class="form-check-label" for="role_name2"> ${employee} </label>
    </div>
    <div class="form-check">
    <input class="form-check-input" type="radio" name="role_name" id="role_name3" value="user">
    <label class="form-check-label" for="role_name3"> ${user} </label>
    </div>
    </div>
                    <div class="form-group">
                        <div class="col-offset-4 col-11">
                            <button type="submit" class="btn btn-primary">${register}</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">${cancel}</button>
            </div>
        </div>
    </div>
</div>

<script  language="javascript">
    function validateReg() {
    let uname, email, psw, psw2;
    let unameText, emailText, pswText;
    let result = true;
    uname = document.forms["registerForm"]["username"].value;
        email = document.forms["registerForm"]["email"].value;
        psw = document.forms["registerForm"]["pass"].value;
        psw2 = document.forms["registerForm"]["pass2"].value;
    const unamePattern = /[a-zA-Z_0-9]{3,15}/;
    if (!unamePattern.test(uname)) {
            unameText = "${eUsername}";
            document.getElementById("unameDemo2").innerHTML = unameText;
            result = false;
        } else if (uname.length < 3) {
            unameText = "${eUsernameMin}";
            document.getElementById("unameDemo2").innerHTML = unameText;
            result = false;
        } else if (uname.length > 14) {
            unameText = "${eUsernameMax}";
            document.getElementById("unameDemo2").innerHTML = unameText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("unameDemo2").innerHTML = unameText;
        }
    const emailPattern = /[a-zA-Z0-9_]+@[A-Za-z0-9].+/;
    if (!emailPattern.test(email)) {
            emailText = "${eMail}";
            document.getElementById("emailDemo2").innerHTML = emailText;
            return false;
        } else {
            unameText = "";
            document.getElementById("emailDemo2").innerHTML = unameText;
        }
    const passPattern = /[a-zA-Z0-9_]{6,32}/;
    if (psw.length < 6) {
            pswText = "${ePasswordMin}";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else if (!passPattern.test(psw)) {
            pswText = "${ePassword}";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else if (psw.length > 32) {
            pswText = "${ePasswordMax}";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else if (psw !== psw2) {
            pswText = "${ePassRepeat}";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("pswDemo2").innerHTML = unameText;
        }
        return result;
    }
</script>



