
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Poliukov_fin</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">HOME</a>
    <div class="collapse navbar-collapse" id="myNavbar">
    <ul class="nav navbar-nav navbar-right">
    <c:if test="${sessionScope.get('user') == null}">
        <li class="sign-up">
        <a data-toggle="modal" data-target="#register" href="#">
        <span class="glyphicon glyphicon-user" > </span>
        | SIGN UP |</a>
        </li>
        <li><a data-toggle="modal" data-target="#login" href="#">
        <span class="glyphicon glyphicon-log-in" ></span>
        | LOGIN |</a>
        </li>
    </c:if>
    <c:if test="${sessionScope.get('user') != null}">
        <li class="sign-up">
        <a href="DispatcherServlet?command=view-user&username=${sessionScope.get('user').login}">
        <span class="glyphicon glyphicon-user" ></span>Hello, ${sessionScope.get('user').login}</a>
        </li>
        <li><a href="DispatcherServlet?command=log-out">
        <span class="glyphicon glyphicon-log-out" ></span>| LOG OUT |</a>
        </li>

            <c:if test='${sessionScope.get("user").role eq "tenant"}'>
                <li>
                    <a href="DispatcherServlet?command=go-to-add-work-request">| MAKE WORK REQUEST |</a>
                </li>

            </c:if>

            <c:if test='${sessionScope.get("user").role eq "user"}'>
            <li> <a href="DispatcherServlet?command=go-to-add-tenant">| MAKE WORK REQUEST |</a>

            </li>
                <li> <a href="DispatcherServlet?command=go-to-add-employee">| BECOME EMPLOYEE |</a>

                </li>
            </c:if>

            <c:if test='${sessionScope.get("user").role eq "admin"}'>
                <li> <a href="DispatcherServlet?command=go-to-update-role">| UPDATE ROLES |</a>

                </li>
            </c:if>
            <c:if test='${sessionScope.get("user").role eq "employee"}'>
                <li> <a href="DispatcherServlet?command=go-to-actual-requests-employee">| ACTUAL REQUESTS |</a>
                </li>
                <li> <a href="DispatcherServlet?command=go-to-show-work-plan">| WORK PLAN |</a>
                </li>
            </c:if>

        <c:if test='${sessionScope.get("user").role eq "dispatcher"}'>
            <li> <a href="DispatcherServlet?command=go-to-all-employee-work-plan">| SHOW WORK PLAN |</a>
            </li>
            <li> <a href="DispatcherServlet?command=go-to-actual-requests-employee">| ASSIGN EMPLOYEES TO WORK REQUESTS |</a>
            </li>
        </c:if>
        </c:if>

    <li> <a href="DispatcherServlet?command=go-to-employee-by-type">| OUR COMMAND |</a>

    </li>


<%--    <li><a href="DispatcherServlet?command=change-language&language=en">| English |</a></li>--%>
<%--    <li><a href="DispatcherServlet?command=change-language&language=ru">| Русский |</a></li>--%>
    </ul>
    </div>
    </nav>



<div id="login" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;  </button>
                <h4 class="modal-title">SIGN IN</h4>
            </div>
            <div class="modal-body text-center">
                <form name="loginForm" class="form-horizontal" method="post" action="DispatcherServlet" onsubmit="return validateForm();">
                    <input type="hidden" name="command" value="login"/>
                    <span id="unameDemo" class="red"></span>
                    <div class="form-group">
                        <label for="username" class="col-sm-5 control-label">username</label>
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
                <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
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
            unameText = "Nickname should be at least 3 symbols.";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else if (uname.length > 14) {
            unameText = "Username should be less then 15 symbols.";
            document.getElementById("unameDemo").innerHTML = unameText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("unameDemo").innerHTML = unameText;
        }
    const passPattern = /[a-zA-Z0-9_]{6,32}/;
    if (psw.length < 4) {
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
        }
        return result;
    }
</script>

<div id="register" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;  </button>
                <h4 class="modal-title" id="myModalLabel">register</h4>
            </div>
            <div class="modal-body text-center">
                <form name="registerForm" class="form-horizontal" method="post" action="DispatcherServlet"  onsubmit="return validateReg();">
                    <input type="hidden" name="command" value="register"/>
                    <div class="form-group">
                        <label for="inputUsername" class="col-sm-3 control-label">username</label>
                        <span id="unameDemo2" class="red"></span>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="inputUsername" placeholder="username"
                                   name="login" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">email</label>
                        <span id="emailDemo2" class="red"></span>
                        <div class="col-sm-7">
                            <input type="email" class="form-control" id="email" placeholder="email"
                                   name="email" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">name</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="name" placeholder="name"
                                   name="name" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="col-sm-3 control-label">surname</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="surname" placeholder="surname"
                                   name="surname" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="second_name" class="col-sm-3 control-label">secondName</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="second_name" placeholder="second_name"
                                   name="second_name" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="col-sm-3 control-label">phone</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="phone" placeholder="phone"
                                   name="phone" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-3 control-label">password</label>
                        <span id="pswDemo2" class="red"></span>
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
    <div class="form-check">
    <input class="form-check-input" type="radio" name="role_name" id="role_name3" value="user">
    <label class="form-check-label" for="role_name3">
    User
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
            unameText = "Username should contain only latin symbols, digits and _";
            document.getElementById("unameDemo2").innerHTML = unameText;
            result = false;
        } else if (uname.length < 3) {
            unameText = "Username should be at least 3 symbols.";
            document.getElementById("unameDemo2").innerHTML = unameText;
            result = false;
        } else if (uname.length > 14) {
            unameText = "Username should be less then 15 symbols.";
            document.getElementById("unameDemo2").innerHTML = unameText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("unameDemo2").innerHTML = unameText;
        }
    const emailPattern = /[a-zA-Z0-9_]+@[A-Za-z0-9].+/;
    if (!emailPattern.test(email)) {
            emailText = "Email should contain latin symbols, @, digits, . and _";
            document.getElementById("emailDemo2").innerHTML = emailText;
            return false;
        } else {
            unameText = "";
            document.getElementById("emailDemo2").innerHTML = unameText;
        }
    const passPattern = /[a-zA-Z0-9_]{6,32}/;
    if (psw.length < 6) {
            pswText = "Password should be at least 6 symbols";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else if (!passPattern.test(psw)) {
            pswText = "Password should contain only latin symbols, digits and _";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else if (psw.length > 32) {
            pswText = "Password should be less then 32 symbols";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else if (psw !== psw2) {
            pswText = "Passwords should be the same";
            document.getElementById("pswDemo2").innerHTML = pswText;
            result = false;
        } else {
            unameText = "";
            document.getElementById("pswDemo2").innerHTML = unameText;
        }
        return result;
    }
</script>



