<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<html>
<head>
    <title>Order</title>
</head>
<body>
<c:if test="${sessionScope.userRole == null}">
    <c:redirect url="controller?command=go_to_login_page"/>
</c:if>

<c:if test="${sessionScope.userRole == 'гость'}">
    <c:redirect url="controller?command=go_to_user_info_page"/>
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

<table border="1" cellspacing="0" cellpadding="2">

    <tr>
        <th>Выберите дату</th>
        <th>Колличество дней</th>
    </tr>


    <tr>
        <form action="controller" method="get">
            <td><input type="datetime-local" name="rentStart" required = "required"
            <c:if test="${sessionScope.isAvailable == true || sessionScope.rentEndDatePageFormat == null}">
                       min="<%= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date())%>"
            </c:if>

            <c:if test="${sessionScope.isAvailable != true && sessionScope.rentEndDatePageFormat != null}">
                       min="${sessionScope.rentEndDatePageFormat}"
            </c:if>
                       value="">
            </td>
            <td>
                <input type="text" name="rentDays" required="required" value="">
            </td>

            <input type="hidden" name="command" value="add_order">
            <input type="submit" value="Далее">
        </form>
    </tr>

</table>
<br>

</body>
</html>
