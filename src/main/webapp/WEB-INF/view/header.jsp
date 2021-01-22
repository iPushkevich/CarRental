<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="main.page" var="mainPage"/>
<fmt:message bundle="${loc}" key="button.ru" var="ruButton"/>
<fmt:message bundle="${loc}" key="button.en" var="enButton"/>
<fmt:message bundle="${loc}" key="user.account" var="account"/>
<fmt:message bundle="${loc}" key="admin.panel" var="adminPanel"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="login" var="login"/>
<fmt:message bundle="${loc}" key="you.are.welcome" var="welcome"/>
<fmt:message bundle="${loc}" key="enter.password" var="password"/>
<fmt:message bundle="${loc}" key="enter.email" var="email"/>
<fmt:message bundle="${loc}" key="sign.in" var="signIn"/>
<fmt:message bundle="${loc}" key="create.account" var="createAccount"/>
<fmt:message bundle="${loc}" key="cars" var="cars"/>
<fmt:message bundle="${loc}" key="users" var="users"/>
<fmt:message bundle="${loc}" key="orders" var="orders"/>
<fmt:message bundle="${loc}" key="for.you" var="forYou"/>

<%@include file="../../css/bootstrap" %>

<html>
<head>
    <style>
        <%@include file="../../css/style.css"%>
    </style>

</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="controller?command=go_to_main_page">PERFECT
                <i class="fa fa-handshake-o" aria-hidden="true"></i> RENT
            <c:if test="${sessionScope.user.name != null && sessionScope.userRole != 'гость'}">
                ${forYou} ${sessionScope.user.name}
            </c:if>
            </a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessionScope.userRole == null}">
                    <li><a href="#myModal" data-toggle="modal" data-target="#myModal" class="nav-link">${login}</a></li>
                </c:if>

                <c:if test="${sessionScope.userRole != null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="far fa-address-card"></i> <span class="caret"></span> </a>
                        <ul class="dropdown-menu">
                            <c:if test="${sessionScope.userRole != null}">
                                <li><a href="controller?command=go_to_user_account_page">${account}</a></li>
                            </c:if>

                            <c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
                                <li role="separator" class="divider"></li>
                                <li><a href="controller?command=show_cars">${cars}</a></li>
                                <li><a href="controller?command=all_users">${users}</a></li>
                                <li><a href="controller?command=all_orders">${orders}</a></li>
                            </c:if>

                            <li role="separator" class="divider"></li>

                            <c:if test="${sessionScope.userRole != null}">
                                <li><a href="controller?command=logout">${logout}</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="change_locale">
                        <%
                            Map<String, String[]> params = request.getParameterMap();

                            String req = "";

                            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                                req += entry.getKey() + "=";
                                for (String s : entry.getValue()) {
                                    req += s + "&";
                                }
                            }
                            session.setAttribute("query", req);
                        %>

                        <c:if test="${sessionScope.locale == 'ru'  || sessionScope.locale == null}">
                            <button type="submit" id="locbutton" class="btn btn-sm btn-block" name="locale"
                                    value="en">${enButton}</button>
                        </c:if>

                        <c:if test="${sessionScope.locale == 'en'}">
                            <button type="submit" id="locbutton" class="btn btn-sm btn-block" name="locale"
                                    value="ru">${ruButton}</button>
                        </c:if>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="myModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">х</button>
                <h4 class="modal-title text-center">${signIn}</h4>
            </div>
            <div class="modal-body">
                <form role="form" action="controller" method="post">
                    <input type="hidden" name="command" value="login">

                    <div class="form-group text-center">
                        <label for="email">
                            <input type="email" name="email" class="form-control" id="email" required
                                   placeholder="${email}">
                        </label>
                        <br>
                        <label for="password">
                            <input type="password" name="password" class="form-control" id="password"
                                   required maxlength="20"
                                   minlength="6" placeholder="${password}">
                        </label>
                        <br>
                        <button type="submit" class="btn btn-success">
                            <i class="glyphicon glyphicon-ok"></i>
                            ${login}
                        </button>
                        <a class="btn btn-primary" href="controller?command=go_to_registration_page"
                           role="button">${createAccount}</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<br>


<%--<header>--%>
<%--    <div class="navbar navbar-inverse navbar-fixed-top">--%>
<%--        <div class="container">--%>
<%--            <div class="navbar-header">--%>
<%--                <button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">--%>
<%--                    <span class="icon-bar"></span>--%>
<%--                    <span class="icon-bar"></span>--%>
<%--                    <span class="icon-bar"></span>--%>
<%--                </button>--%>
<%--                <a class="navbar-brand" href="controller?command=go_to_main_page">Perfect Rent</a>--%>
<%--            </div>--%>
<%--            <div class="navbar-collapse collapse">--%>
<%--                <form class="navbar-form navbar-right">--%>
<%--                    <div class="form-group">--%>
<%--                        <input type="text" placeholder="email" class="form-control">--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <input type="text" placeholder="password" class="form-control">--%>
<%--                    </div>--%>
<%--                    <button type="submit" class="btn btn-success">Login</button>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</header>--%>

<%--<header>--%>
<%--    <%@include file="../css/bootstrap.css" %>--%>
<%--    <h6 align="right" style="color:Black">--%>
<%--        <form action="controller" method="post">--%>
<%--            <input type="hidden" name="command" value="change_locale">--%>
<%--            <%--%>
<%--                Map<String, String[]> params = request.getParameterMap();--%>

<%--                String req = "";--%>

<%--                for (Map.Entry<String, String[]> entry : params.entrySet()) {--%>
<%--                    req += entry.getKey() + "=";--%>
<%--                    for (String s : entry.getValue()) {--%>
<%--                        req += s + "&";--%>
<%--                    }--%>
<%--                }--%>
<%--                session.setAttribute("query", req);--%>
<%--            %>--%>
<%--            <c:if test="${sessionScope.locale == 'ru'  || sessionScope.locale == null}">--%>
<%--                <button type="submit" name="locale" value="en">${enButton}</button>--%>
<%--            </c:if>--%>

<%--            <c:if test="${sessionScope.locale == 'en'}">--%>
<%--                <button type="submit" name="locale" value="ru">${ruButton}</button>--%>
<%--            </c:if>--%>
<%--        </form>--%>
<%--    </h6>--%>
<%--    <h2 align="center" style="color:Black"><a href="controller?command=go_to_main_page">${mainPage}</a>--%>
<%--        <c:if test="${sessionScope.userRole == null}">--%>
<%--            <div class="container text-left">--%>
<%--                <button class="btn btn-info" data-toggle="modal" data-target="#myModal">${login}</button>--%>
<%--                <div id="myModal" class="modal fade" tabindex="-1">--%>
<%--                    <div class="modal-dialog modal-sm">--%>
<%--                        <div class="modal-content">--%>
<%--                            <div class="modal-header">--%>
<%--                                <button class="close" data-dismiss="modal">х</button>--%>
<%--                                <h4 class="modal-title text-center">${signIn}</h4>--%>
<%--                            </div>--%>
<%--                            <div class="modal-body">--%>
<%--                                <form role="form" action="controller" method="post">--%>
<%--                                    <input type="hidden" name="command" value="login">--%>

<%--                                    <div class="form-group text-center">--%>
<%--                                        <label for="email">--%>
<%--                                            <input type="email" name="email" class="form-control" id="email" required--%>
<%--                                                   placeholder="${email}">--%>
<%--                                        </label>--%>
<%--                                        <br>--%>
<%--                                        <label for="password">--%>
<%--                                            <input type="password" name="password" class="form-control" id="password"--%>
<%--                                                   required maxlength="20"--%>
<%--                                                   minlength="6" placeholder="${password}">--%>
<%--                                        </label>--%>
<%--                                        <br>--%>
<%--                                        <button type="submit" class="btn btn-success">--%>
<%--                                            <i class="glyphicon glyphicon-ok"></i>--%>
<%--                                                ${login}--%>
<%--                                        </button>--%>
<%--                                        <a class="btn btn-primary" href="controller?command=go_to_registration_page" role="button">${createAccount}</a>--%>
<%--                                    </div>--%>
<%--                                </form>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            &lt;%&ndash;            <h3 align="left" style="color:Black"><a href="controller?command=go_to_login_page">${login}</a></h3>&ndash;%&gt;--%>
<%--        </c:if>--%>

<%--        <c:if test="${sessionScope.user != null && sessionScope.userRole != 'гость'}">--%>
<%--            <h3 align="left" style="color:Black">${welcome} ${sessionScope.user.name}</h3>--%>
<%--        </c:if>--%>

<%--        <c:if test="${sessionScope.userRole != null}">--%>
<%--            <h3 align="left" style="color:Black"><a href="controller?command=go_to_user_account_page"><p>${account}</p>--%>
<%--            </a></h3>--%>
<%--        </c:if>--%>

<%--        <c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">--%>
<%--            <h3 align="left" style="color:Black"><a href="controller?command=go_to_admin_panel"><p>${adminPanel}</p>--%>
<%--            </a></h3>--%>
<%--        </c:if>--%>

<%--        <c:if test="${sessionScope.userRole != null}">--%>
<%--            <h3 align="left" style="color:Black"><a href="controller?command=logout">${logout}</a></h3>--%>
<%--        </c:if>--%>
<%--    </h2>--%>
<%--</header>--%>

</body>
</html>
