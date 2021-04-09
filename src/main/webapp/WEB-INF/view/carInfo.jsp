<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<html>
<head>
    <title>Car Info</title>
</head>
<body>
<c:if test="${requestScope == null}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
<a href="controller?command=show_cars">Список авто</a>
</c:if>

<br>

<h2 align="center" style="color:Black">Информация об авто</h2>

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

<h2 align="center" style="color:Black">Информация о прокате</h2>

<table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <th>Описание</th>
        <th>Стоимость</th>
        <th>Рейтинг</th>
        <th>Статус</th>
        <th>Отзывы</th>
    </tr>

    <tr>
        <td>${sessionScope.carRentInfo.description}</td>
        <td>${sessionScope.carRentInfo.rentCost}</td>
        <td>${sessionScope.carRentInfo.carRating}</td>
        <td><c:if test="${sessionScope.isAvailable == true}">
            <c:out value="Доступен"/>
        </c:if>
            <c:if test="${sessionScope.isAvailable == false}">
                <c:out value="Забронирован по ${sessionScope.rentEndDate}"/>
            </c:if>
        </td>
        <td>

            <c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
            <form action="controller" method="get">
                <input type="hidden" name="command" value="car_info">
                <input type="hidden" name="carID" value="${sessionScope.carID}">
                <input type="text" name="addReview" value="">
                <input type="submit" value="Добавить отзыв">
            </form>
            </c:if>

            <c:forEach var="review" items="${sessionScope.reviews}">
                <c:out value="${review}"/>

                <c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="car_info">
                    <input type="hidden" name="deleteReview" value="${review}">
                    <input type="hidden" name="carID" value="${sessionScope.carID}">
                    <input type="submit" value="Удалить">
                </form>
                </c:if>

            </c:forEach>
        </td>
    </tr>
</table>

<br>
<h2 align="center" style="color:Black"><a href="controller?command=go_to_order_page">Заказать</a></h2>
<br>

<c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
    <h2 align="center" style="color:Black"><a href="controller?command=go_to_new_car_page">Изменить</a></h2>
</c:if>

<br>

<h2>${sessionScope.carInfoChanged}</h2>
<%session.removeAttribute("carInfoChanged");%>
<%session.removeAttribute("rentEndDate");%>

</body>
</html>

