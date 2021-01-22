<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="personal.info" var="personalInfo"/>
<fmt:message bundle="${loc}" key="change" var="change"/>
<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="surname" var="surname"/>
<fmt:message bundle="${loc}" key="age" var="age"/>
<fmt:message bundle="${loc}" key="phone.number" var="phone"/>
<fmt:message bundle="${loc}" key="passport" var="passport"/>
<fmt:message bundle="${loc}" key="driving.experience" var="drivingExp"/>
<fmt:message bundle="${loc}" key="road.accident" var="roadAccident"/>
<fmt:message bundle="${loc}" key="previous.rent" var="previousRent"/>
<fmt:message bundle="${loc}" key="discount" var="discount"/>
<fmt:message bundle="${loc}" key="my.orders" var="myOrders"/>
<fmt:message bundle="${loc}" key="order.number" var="number"/>
<fmt:message bundle="${loc}" key="order.start" var="start"/>
<fmt:message bundle="${loc}" key="order.end" var="end"/>
<fmt:message bundle="${loc}" key="order.status" var="status"/>
<fmt:message bundle="${loc}" key="preliminary.cost" var="preCost"/>
<fmt:message bundle="${loc}" key="finally.cost" var="finallyCost"/>
<fmt:message bundle="${loc}" key="car" var="car"/>
<fmt:message bundle="${loc}" key="all.users" var="allUsers"/>

<html>
<head>
    <title>User Account</title>
</head>
<body>
<c:if test="${sessionScope.userRole == null}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<h3 align="center" style="color:Black">${personalInfo} <a href="controller?command=go_to_user_info_page">${change}</a>
</h3>

<table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <th>${name}</th>
        <th>${surname}</th>
        <th>${age}</th>
        <th>${phone}</th>
        <th>${passport}</th>
        <th>${drivingExp}</th>
        <th>${roadAccident}</th>
        <th>${previousRent}</th>
        <th>${discount}</th>
    </tr>
    <tr>
        <td>${sessionScope.user.name}</td>
        <td>${sessionScope.user.surName}</td>
        <td>${sessionScope.user.age}</td>
        <td>${sessionScope.user.phoneNumber}</td>
        <td>${sessionScope.user.passport}</td>
        <td>${sessionScope.user.drivingExperience}</td>
        <td>${sessionScope.user.roadAccidentCount}</td>
        <td>${sessionScope.user.previousRentCount}</td>
        <td>${sessionScope.user.discount}</td>
    </tr>
</table>

<form action="controller" method="get">
    <input type="hidden" name="command" value="user_orders">
    <input type="hidden" name="userID" value="${sessionScope.userID}">
    <input type="submit" value="${myOrders}">
</form>

<h3>${requestScope.noOrders}</h3>
<c:if test="${requestScope.orders != null}">
    <h3 align="center" style="color:Black">${myOrders}</h3>
    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <th>${number}</th>
            <th>${start}</th>
            <th>${end}</th>
            <th>${preCost}</th>
            <th>${finallyCost}</th>
            <th>${status}</th>
            <th>${car}</th>
        </tr>

        <c:forEach items="${requestScope.orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.startRentDate.toLocaleString()}</td>
                <td>${order.endRentDate.toLocaleString()}</td>
                <td>${order.cost}</td>
                <td>${order.finalCost}</td>
                <td>${order.status}
                    <c:if test="${order.status == 'declined'}">
                        : ${order.declineReason}
                    </c:if>
                </td>
                <td>${order.carID}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h2 align="center" style="color:Black">${sessionScope.wrongInput}</h2>
<%session.removeAttribute("wrongInput");%>

<h2 align="center" style="color:Black">${sessionScope.dataChanged}</h2>
<%session.removeAttribute("dataChanged");%>
<br>
<c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
    <h3><a href="controller?command=all_users">${allUsers}</a></h3>
</c:if>

</body>
</html>
