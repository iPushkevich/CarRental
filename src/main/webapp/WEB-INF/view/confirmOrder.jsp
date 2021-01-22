<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<html>
<head>
    <title>Order Info</title>
</head>
<body>
<c:if test="${sessionScope.order == null}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

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
    </tr>

    <tr>
        <td>${sessionScope.car.brand}</td>
        <td>${sessionScope.car.model}</td>
        <td>${sessionScope.car.year}</td>
        <td>${sessionScope.car.mileage}</td>
        <td>${sessionScope.car.engineType}</td>
        <td>${sessionScope.car.engineSize}</td>
        <td>${sessionScope.car.transmissionType}</td>
        <td>${sessionScope.car.wheelsDrive}</td>
        <td>${sessionScope.car.type}</td>
    </tr>
</table>
<br>

<c:if test="${sessionScope.orderCreated == null}">
    <h3 align="center" style="color:Black">Забронировано: с ${requestScope.startDate} по ${requestScope.endDate}</h3>

    <h3 align="center" style="color:Black">Ваш контактный номер: ${requestScope.userPhone}</h3>

    <h3 align="center" style="color:Black">Ваша скидка: ${requestScope.discount}%</h3>

    <h3 align="center" style="color:Black">Итоговая стоимость: ${requestScope.finalCost}</h3>
</c:if>

<c:if test="${requestScope.endDate != null}">
    <form action="controller" method="get">
        <input type="hidden" name="command" value="add_order">
        <input type="hidden" name="confirmOrder" value="1">
        <input type="hidden" name="startDate" value="${requestScope.startDate}">
        <input type="submit" value="Подтвердить">
    </form>
</c:if>
<h2 align="center" style="color:Black">${sessionScope.orderCreated}</h2>




<c:if test="${sessionScope.orderCreated != null}">
    <%session.removeAttribute("order");%>
</c:if>

<%session.removeAttribute("orderCreated");%>

</body>
</html>
