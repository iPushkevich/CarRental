<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Home Page</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<%--<div class="jumbotron">--%>
<%--    <div class="container text-center">--%>
<%--        <h2>Мы предоставляем лучшие предложения для наших клиентов </h2>--%>
<%--    </div>--%>
<%--    <div class="container">--%>
<%--        <div class="row">--%>
<%--            <div class="col-md-4">--%>
<%--                <h5>Приведи друга и получите скидку 20% каждый</h5>--%>
<%--                <a href="#" class="btn btn-primary btn-md">Подробнее &raquo;</a>--%>
<%--            </div>--%>
<%--            <div class="col-md-4">--%>
<%--                <h5>Каждый 10-й час бесплатно</h5>--%>
<%--                <a href="#" class="btn btn-primary btn-md">Подробнее &raquo;</a>--%>
<%--            </div>--%>
<%--            <div class="col-md-4">--%>
<%--                <h5>Скидки в день рождения</h5>--%>
<%--                <a href="#" class="btn btn-primary btn-md">Подробнее &raquo;</a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<div id="headerwrap">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-8 col-lg-offset-2">
                <h1>Мы предлагаем лучшие условия для наших клиентов</h1>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h4>Приведи друга и получите скидку 20% каждый</h4>
                        <a href="#" class="btn btn-primary btn-md">Подробнее &raquo;</a>
                    </div>
                    <div class="col-md-4">
                        <h4>Каждый 10-й час бесплатно</h4>
                        <a href="#" class="btn btn-primary btn-md">Подробнее &raquo;</a>
                    </div>
                    <div class="col-md-4">
                        <h4>Скидки в день рождения</h4>
                        <a href="#" class="btn btn-primary btn-md">Подробнее &raquo;</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<form action="controller" method="get" class="centered">
    <input type="hidden" name="command" value="select_car">
    <c:forEach var="brand" items="${requestScope.brands}">
            <button class="btn btn-sm" type="submit" name="brand" value="${brand}">
                <div class="tilt">
                <img src="${pageContext.request.contextPath}/css/img/${brand.toLowerCase()}.jpg" width="200"
                     height="200" alt="${brand}"/>
                </div>
            </button>
    </c:forEach>
</form>

</body>
</html>