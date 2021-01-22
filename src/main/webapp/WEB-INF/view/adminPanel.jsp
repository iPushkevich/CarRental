<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<html>
<head>
    <title>Admin Panel</title>
</head>
<body>
<c:if test="${sessionScope.userRole != 'администратор' && sessionScope.userRole != 'владелец'}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<%session.removeAttribute("car");%>
<%session.removeAttribute("carRentInfo");%>

<%--<h1 align="center" style="color:Black"><a href="controller?command=show_cars">Автомобили</a></h1>--%>
<%--<h1 align="center" style="color:Black"><a href="controller?command=all_users">Пользователи</a></h1>--%>
<%--<h1 align="center" style="color:Black"><a href="controller?command=all_orders">Заказы</a></h1>--%>


<c:if test="${requestScope.cars != null}">

    <h1>Все авто</h1>

    <h2>${sessionScope.carAdded}</h2>
    <%session.removeAttribute("carAdded");%>


    <table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <th>Марка</th>
        <th>Модель</th>
        <th>Год выпуска</th>
        <th>Пробег</th>
        <th>Тип топлива</th>
        <th>Объем двигателя</th>
        <th>Коробка передач</th>
        <th>Привод</th>
        <th>Тип кузова</th>
        <th>Информация</th>
    </tr>

    <c:forEach items="${requestScope.cars}" var="car">
        <tr>
            <td>${car.value.brand}</td>
            <td>${car.value.model}</td>
            <td>${car.value.year}</td>
            <td>${car.value.mileage}</td>
            <td>${car.value.engineType}</td>
            <td>${car.value.engineSize}</td>
            <td>${car.value.transmissionType}</td>
            <td>${car.value.wheelsDrive}</td>
            <td>${car.value.type}</td>
            <td>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="car_info">
                    <input type="hidden" name="carID" value="${car.key}">
                    <input type="submit" value="Подробнее">
                </form>
            </td>
        </tr>
    </c:forEach>
    <br>
</table>
    <h3><a href="controller?command=go_to_new_car_page">Добавить авто</a></h3>
</c:if>

</body>
</html>
