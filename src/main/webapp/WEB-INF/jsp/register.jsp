
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set scope="session" var="previousQuery" value="index.jsp"/>

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
<c:import url="menu/menu.jsp"/>
<script language="javascript">
    function active() {
        document.getElementById("index-page").className = "active";
    }
</script>
<div class="container-fluid text-center wrapper">
    <div class="row content">


        <div class="col-sm-8 text-left mainContent">

            <div class="modal-dialog">
                <div class="modal-content">

                    <c:if test="${requestScope.get('errorMessage')!=null}">
                        <h4 class="red text-center"><c:out value="${requestScope.get('errorMessage')}"/></h4>
                        <c:remove var="errorMessage" scope="request"/>
                    </c:if>
                    <div class="modal-body text-center">
                        <form name="registerForm" class="form-horizontal" method="post" action="DispatcherServlet" onsubmit="return validateForm();">
                            <input type="hidden" name="command" value="register"/>
                            <div class="form-group">
                                <label for="inputUsername" class="col-sm-3 control-label">username</label>
                                <span id="unameDemo" class="red"></span>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="inputUsername" placeholder="username"
                                           name="login" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-sm-3 control-label">email</label>
                                <span id="emailDemo" class="red"></span>
                                <div class="col-sm-7">
                                    <input type="email" class="form-control" id="email" placeholder="email"
                                           name="email" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-3 control-label">name</label>
                                <div class="col-sm-7">
                                    <input type="name" class="form-control" id="name" placeholder="name"
                                           name="name" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="surname" class="col-sm-3 control-label">surname</label>
                                <div class="col-sm-7">
                                    <input type="surname" class="form-control" id="surname" placeholder="surname"
                                           name="surname" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="second_name" class="col-sm-3 control-label">second_name</label>
                                <div class="col-sm-7">
                                    <input type="second_name" class="form-control" id="second_name" placeholder="second_name"
                                           name="second_name" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="phone" class="col-sm-3 control-label">phone</label>
                                <div class="col-sm-7">
                                    <input type="phone" class="form-control" id="phone" placeholder="phone"
                                           name="phone" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputPassword3" class="col-sm-3 control-label">password</label>
                                <span id="pswDemo" class="red"></span>
                                <div class="col-sm-7">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="password"
                                           name="password" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword4" class="col-sm-3 control-label">repeatPassword</label>
                                <div class="col-sm-7">
                                    <input type="password" class="form-control" id="inputPassword4" placeholder="repeatPassword"
                                           name="password2" required>
                                </div>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="role_name" id="role_name1" value="tenant" checked>
                                <label class="form-check-label" for="role_name1">
                                    Tenant
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="role_name" id="role_name2" value="employee">
                                <label class="form-check-label" for="role_name2">
                                    Employee
                                </label>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-7">
                                    <button type="submit" class="btn btn-primary">register</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">cancel</button>
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
            unameText = "Username should contain only latin symbols, digits and _";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length < 3) {
            unameText = "Username should be at least 3 symbols.";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length > 14) {
            unameText = "Username should be less then 15 symbols.";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        }  else {
            unameText = "";
            document.getElementById("unameDemo").innerHTML = unameText;
        }
        const emailPattern = /[a-zA-Z0-9_]+@[A-Za-z0-9].+/;
        if (!emailPattern.test(email)) {
            emailText = "Email should contain latin symbols, @, digits, . and _";
            document.getElementById("emailDemo").innerHTML = emailText;
            return false;
        } else {
            unameText = "";
            document.getElementById("emailDemo").innerHTML = unameText;
        }
        const passPattern = /[a-zA-Z0-9_]{3,32}/;
        if (psw.length < 6) {
            pswText = "Password should be at least 6 symbols";
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
        } else if (psw !== psw2) {
            pswText = "Passwords should be the same";
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