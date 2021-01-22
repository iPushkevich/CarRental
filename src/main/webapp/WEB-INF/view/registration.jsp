<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="registration" var="registration"/>
<fmt:message bundle="${loc}" key="enter.email" var="email"/>
<fmt:message bundle="${loc}" key="enter.password" var="password"/>
<fmt:message bundle="${loc}" key="sign.in" var="signIn"/>
<fmt:message bundle="${loc}" key="create.account" var="createAccount"/>
<fmt:message bundle="${loc}" key="incorrect.login.or.password" var="incorrectData"/>
<fmt:message bundle="${loc}" key="already.registered" var="alreadyRegistered"/>
<fmt:message bundle="${loc}" key="min.pass.length" var="minPassLength"/>

<html>
<head>
    <title>REGISTRATION</title>
</head>
<body>
<div class="container text-center">
    <h1>${registration}</h1>
    <form role="form" action="controller" method="post">
        <input type="hidden" name="command" value="add_new_user">

        <div class="form-group text-center">
            <label for="email">
                <input type="email" name="email" class="form-control" id="email" required placeholder="${email}">
            </label>
            <br>
            <label for="password">
                <input type="password" name="password" class="form-control" id="password" required maxlength="20"
                       minlength="6" placeholder="${password}" data-toggle="tooltip" title="${minPassLength}"
                       data-placement="right">
            </label>
            <br>
            <button type="submit" class="btn btn-success">
                <i class="glyphicon glyphicon-ok"></i>
                ${createAccount}
            </button>
        </div>
    </form>

    <c:if test="${requestScope.error != null}">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <c:if test="${requestScope.error == 'This email is already registered'}">
                    <h3>${alreadyRegistered}</h3>
                </c:if>

                <c:if test="${requestScope.error == 'Incorrect login or password'}">
                    <h3>${incorrectData}</h3>
                </c:if>
            </div>
        </div>
    </c:if>

    <c:if test="${requestScope.error != null}">
        <a href="controller?command=go_to_login_page">${signIn}</a>
    </c:if>
</div>
<script>
    $(document).ready(function (){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
</body>
</html>
