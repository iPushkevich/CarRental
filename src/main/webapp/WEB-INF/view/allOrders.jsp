<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<html>
<head>
    <title>All Orders</title>
</head>
<body>
<c:if test="${sessionScope.userRole != 'администратор' && sessionScope.userRole != 'владелец'}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<h3>Поиск заказа</h3>
<form action="controller" method="get">
    <input type="hidden" name="command" value="all_orders">

    <p><input type="radio" name="searchCriteria" value="orderIDSearch" id="orderID">
        <label for="orderID"> По номеру заказа </label>

    <p><input type="radio" name="searchCriteria" value="phoneNumberSearch" id="phone">
        <label for="phone">По номеру телефона</label>

    <p><input type="radio" name="searchCriteria" value="nameSurnameSearch" id="nameSurname">
        <label for="nameSurname">По имени и фамилии</label>
    <p>
        <input type="submit" value="Далее">
</form>

<c:if test="${requestScope.phoneNumberSearch != null || requestScope.nameSurnameSearch != null || requestScope.orderIDSearch != null}">
    <form action="controller" method="get">
        <input type="hidden" name="command" value="all_orders">
        <c:if test="${requestScope.orderIDSearch != null}">
            <input type="hidden" name="searchBy" value="orderIDSearch">
            <input type="text" name="orderID" required="required" value=""> Введите номер заказа
            <input type="submit" value="Поиск">
        </c:if>

        <c:if test="${requestScope.phoneNumberSearch != null}">
            <input type="hidden" name="searchBy" value="phoneNumberSearch">
            <input type="text" name="phoneNumber" required="required" minlength="9" maxlength="9"
                   value=""> Введите номер телефона (9 цифр)
            <input type="submit" value="Поиск">
        </c:if>

        <c:if test="${requestScope.nameSurnameSearch != null}">
            <input type="hidden" name="searchBy" value="nameSurnameSearch">
            Введите имя:
            <input type="text" name="name" required="required" value="">
            Введите фамилию:
            <input type="text" name="surname" required="required" value="">
            <input type="submit" value="Поиск">
        </c:if>
    </form>
</c:if>
${requestScope.wrongInput}
<br>
<c:if test="${requestScope.newOrders == null}">
    <form name="controller" method="get">
        <input type="hidden" name="command" value="all_orders">
        <input type="hidden" name="newOrders" value="1">
        <input type="submit" value="Новые заказы">
    </form>
</c:if>
<c:if test="${requestScope.newOrders != null}">
    <form name="controller" method="get">
        <input type="hidden" name="command" value="all_orders">
        <input type="submit" value="Все заказы">
    </form>
</c:if>

<c:if test="${requestScope.newOrders == null && requestScope.noOrders == null}">
    <h2 align="center" style="color:Black">Все заказы</h2>
</c:if>

<c:if test="${requestScope.newOrders != null && requestScope.noOrders == null}">
    <h2 align="center" style="color:Black">Новые заказы</h2>
</c:if>

<c:if test="${requestScope.noOrders == null}">
    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <th>Номер заказа</th>
            <th>С</th>
            <th>По</th>
            <th>Предварительная стоимость</th>
            <th>Итоговая стоимость</th>
            <th>Статус</th>
            <th>Пользователь</th>
            <th>Автомобиль</th>
            <th>Подробнее</th>
        </tr>

        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.startRentDate}</td>
                <td>${order.endRentDate}</td>
                <td>${order.cost}</td>
                <td>${order.finalCost}</td>
                <td>${order.status}</td>
                <td>${order.userID}</td>
                <td>${order.carID}</td>
                <td>
                    <form name="controller" method="get">
                        <input type="hidden" name="command" value="order_info">
                        <input type="hidden" name="orderID" value="${order.id}">
                        <input type="submit" value="Подробнее">
                    </form>
                </td>

            </tr>
        </c:forEach>
    </table>
</c:if>

<h2 align="center">${requestScope.noOrders}</h2>



</body>
</html>
