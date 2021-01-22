<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<html>
<head>
    <title>Order Info</title>
</head>
<body>
<c:if test="${sessionScope.userRole == null}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<h2 align="center" style="color:Black">Пользователь</h2>
<table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Возраст</th>
        <th>Телефон</th>
        <th>Серия и номер паспорта</th>
        <th>Стаж вождения</th>
        <th>Колличество ДТП</th>
        <th>Предыдущие заказы</th>
        <th>Скидка</th>
    </tr>
    <tr>
        <td>${requestScope.user.name}</td>
        <td>${requestScope.user.surName}</td>
        <td>${requestScope.user.age}</td>
        <td>${requestScope.user.phoneNumber}</td>
        <td>${requestScope.user.passport}</td>
        <td>${requestScope.user.drivingExperience}</td>
        <td>${requestScope.user.roadAccidentCount}</td>
        <td>${requestScope.user.previousRentCount}</td>
        <td>${requestScope.user.discount}</td>
    </tr>
</table>

<h2 align="center" style="color:Black">Автомобиль</h2>
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
        <td>${requestScope.car.brand}</td>
        <td>${requestScope.car.model}</td>
        <td>${requestScope.car.year}</td>
        <td>${requestScope.car.mileage}</td>
        <td>${requestScope.car.engineType}</td>
        <td>${requestScope.car.engineSize}</td>
        <td>${requestScope.car.transmissionType}</td>
        <td>${requestScope.car.wheelsDrive}</td>
        <td>${requestScope.car.type}</td>
    </tr>
</table>

<h2 align="center" style="color:Black">Заказ</h2>
<table border="1" cellspacing="0" cellpadding="2">

    <tr>
        <th>Номер заказа</th>
        <th>С</th>
        <th>По</th>
        <th>Предварительная стоимость</th>
        <th>Итоговая стоимость</th>
        <th>Статус</th>
        <c:if test="${requestScope.order.status == 'declined' || requestScope.declined != null}">
            <th>
                Причина отказа
            </th>
        </c:if>
    </tr>

    <tr>
        <td>${requestScope.order.id}</td>
        <td>${requestScope.order.startRentDate}</td>
        <td>${requestScope.order.endRentDate}</td>
        <td>${requestScope.order.cost}</td>
        <td>${requestScope.order.finalCost}</td>

        <td>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="order_info">
                <input type="hidden" name="orderID" value="${requestScope.order.id}">
                <select name="newOrderStatus">
                    <option value="${requestScope.order.status}"> ${requestScope.order.status} </option>
                    <option value="accepted"> подтвержден </option>
                    <option value="declined"> отказано </option>
                    <option value="processed"> обрабатывается </option>
                </select>
                <input type="submit" value="Применить">
            </form>
        </td>


        <c:if test="${requestScope.declined != null || requestScope.order.declineReason != null}">
            <td>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="order_info">
                    <input type="hidden" name="orderID" value="${requestScope.order.id}">
                    <input type="hidden" name="declined" value="1">
                    <input type="text" name="declineReason" value="${requestScope.order.declineReason}">
                    <input type="submit" value="Применить">
                </form>
            </td>
        </c:if>
    </tr>
</table>
<h2 align="center" style="color:Black">${requestScope.statusChanged}</h2>

</body>
</html>
