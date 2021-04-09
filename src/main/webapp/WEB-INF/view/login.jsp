<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="sign.in" var="signIn"/>
<fmt:message bundle="${loc}" key="create.account" var="createAccount"/>
<fmt:message bundle="${loc}" key="enter.email" var="email"/>
<fmt:message bundle="${loc}" key="enter.password" var="password"/>
<fmt:message bundle="${loc}" key="user.not.found" var="userNotFound"/>
<fmt:message bundle="${loc}" key="wrong.password" var="wrongPassword"/>

<html>
<head>
    <title>LOGIN</title>
</head>
<body>
<div class="container text-center">
    <h1>${signIn}</h1>
    <form role="form" action="controller" method="post">
        <input type="hidden" name="command" value="login">

        <div class="form-group">
            <label for="email">
                <input type="email" name="email" class="form-control" id="email" required placeholder="${email}">
            </label>
            <br>
            <label for="password">
                <input type="password" name="password" class="form-control" id="password" required maxlength="20"
                       minlength="6" placeholder="${password}">
            </label>
            <br>
            <button type="submit" class="btn btn-success">
                <i class="glyphicon glyphicon-ok"></i>
                ${login}
            </button>
        </div>

    </form>

    <c:if test="${requestScope.error != null}">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <c:if test="${requestScope.error == 'User not found'}">
                    <h3>${userNotFound}</h3>
                </c:if>

                <c:if test="${requestScope.error == 'Wrong password'}">
                    <h3>${wrongPassword}</h3>
                </c:if>
            </div>
        </div>
    </c:if>

    <a href="controller?command=go_to_registration_page">${createAccount}</a>
</div>
</body>
</html>
