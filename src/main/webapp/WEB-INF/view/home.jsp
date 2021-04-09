<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="header.jsp" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="logo" var="logo"/>
<fmt:message bundle="${loc}" key="more.info" var="moreInfo"/>
<fmt:message bundle="${loc}" key="friend.discount" var="friendDiscount"/>
<fmt:message bundle="${loc}" key="10th.hour.free" var="free10thHoure"/>
<fmt:message bundle="${loc}" key="birthday.discount" var="birthdayDiscount"/>

<html>
<head>
    <title>Home Page</title>
</head>
<body>
<div id="headerwrap">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-8 col-lg-offset-2">
                <h1>${logo}</h1>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h4>${friendDiscount}</h4>
                        <a href="#" class="btn btn-primary btn-md">${moreInfo} &raquo;</a>
                    </div>
                    <div class="col-md-4">
                        <h4>${free10thHoure}</h4>
                        <a href="#" class="btn btn-primary btn-md">${moreInfo} &raquo;</a>
                    </div>
                    <div class="col-md-4">
                        <h4>${birthdayDiscount}</h4>
                        <a href="#" class="btn btn-primary btn-md">${moreInfo} &raquo;</a>
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
                <img src="${pageContext.request.contextPath}/css/img/brand/${brand.toLowerCase()}.jpg" width="200"
                     height="200" alt="${brand}"/>
                </div>
            </button>
    </c:forEach>
</form>

</body>
</html>