<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:message bundle="${locale}" key="locale.menu.login1" var="login1"/>
<fmt:message bundle="${locale}" key="locale.updateRole.tenant" var="tenant"/>
<fmt:message bundle="${locale}" key="locale.updateRole.employee" var="employee"/>
<fmt:message bundle="${locale}" key="locale.common.password" var="password"/>
<fmt:message bundle="${locale}" key="locale.common.cancel" var="cancel"/>
<fmt:message bundle="${locale}" key="locale.common.username" var="username"/>
<fmt:message bundle="${locale}" key="locale.common.register" var="register"/>
<fmt:message bundle="${locale}" key="locale.common.repeatPassword" var="repeatPassword"/>
<fmt:message bundle="${locale}" key="locale.common.name" var="name"/>
<fmt:message bundle="${locale}" key="locale.common.secondName" var="secondName"/>
<fmt:message bundle="${locale}" key="locale.common.email" var="email"/>
<fmt:message bundle="${locale}" key="locale.common.phone" var="phone"/>
<fmt:message bundle="${locale}" key="locale.common.surname" var="surname"/>
<fmt:message bundle="${locale}" key="locale.common.user" var="user"/>
<fmt:message bundle="${locale}" key="locale.menu.eUsername" var="eUsername"/>
<fmt:message bundle="${locale}" key="locale.menu.eUsernameMax" var="eUsernameMax"/>
<fmt:message bundle="${locale}" key="locale.menu.ePasswordMin" var="ePasswordMin"/>
<fmt:message bundle="${locale}" key="locale.menu.ePassword" var="ePassword"/>
<fmt:message bundle="${locale}" key="locale.menu.ePasswordMax" var="ePasswordMax"/>
<fmt:message bundle="${locale}" key="locale.menu.eUsernameMin" var="eUsernameMin"/>
<fmt:message bundle="${locale}" key="locale.menu.eMail" var="eMail"/>
<fmt:message bundle="${locale}" key="locale.menu.ePassRepeat" var="ePassRepeat"/>


<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>




</head>
<body onload="active()">
<script language="javascript">
    function active() {
        document.getElementById("index-page").className = "active";
    }
</script>
<c:import url="menu/menu.jsp"/>

<c:if test="${requestScope.get('errorMessage')!=null}">
    <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
    <c:remove var="errorMessage" scope="request"/>
</c:if>

<div class="container-fluid text-center wrapper">
    <div class="row content">
        <div class="col-2 text-left mainContent">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <c:if test="${requestScope.get('errorMessage')!=null}">
                        <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
                        <c:remove var="errorMessage" scope="request"/>
                    </c:if>
                    <div class="modal-body text-center">
                        <form name="registerForm" class="form-horizontal" method="post" action="DispatcherServlet" onsubmit="return validateForm();">
                            <input type="hidden" name="command" value="register"/>
                            <div class="form-group">
                                <label for="inputUsername" class="col-4 control-label">${username}</label>
                                <span id="unameDemo" class="red"></span>
                                <div class="col-11">
                                    <input type="text" class="form-control" id="inputUsername" placeholder="${username}"
                                           name="login" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-10 control-label">${email}}</label>
                                <span id="emailDemo" class="red"></span>
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
                                <label for="inputPassword3" class="col-2 control-label">${password}</label>
                                <span id="pswDemo" class="red"></span>
                                <div class="col-9">
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
                                <label class="form-check-label" for="role_name1">${tenant}</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="role_name" id="role_name2" value="employee">
                                <label class="form-check-label" for="role_name2">${employee}</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="role_name" id="role_name3" value="user">
                                <label class="form-check-label" for="role_name3">${user}</label>
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

    </div>
</div>



<script  language="javascript">
    function validateForm() {
        let uname, email, psw, psw2;
        let unameText, emailText, pswText;
        let result = true;
        uname = document.forms["registerForm"]["username"].value;
        email = document.forms["registerForm"]["email"].value;
        psw = document.forms["registerForm"]["pass"].value;
        psw2 = document.forms["registerForm"]["pass2"].value;
        let unamePattern = /[a-zA-Z_0-9]{3,15}/;
        if (!unamePattern.test(uname)) {
            unameText = "${eUsername}";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length < 3) {
            unameText = "${eUsernameMin}";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length > 14) {
            unameText = "${eUsernameMax}";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        }  else {
            unameText = "";
            document.getElementById("unameDemo").innerHTML = unameText;
        }
        const emailPattern = /[a-zA-Z0-9_]+@[A-Za-z0-9].+/;
        if (!emailPattern.test(email)) {
            emailText = "${eMail}";
            document.getElementById("emailDemo").innerHTML = emailText;
            return false;
        } else {
            unameText = "";
            document.getElementById("emailDemo").innerHTML = unameText;
        }
        const passPattern = /[a-zA-Z0-9_]{3,32}/;
        if (psw.length < 6) {
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
        } else if (psw !== psw2) {
            pswText = "${ePassRepeat}";
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