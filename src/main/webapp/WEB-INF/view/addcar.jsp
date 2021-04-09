<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<html>
<head>
    <title>Add new car</title>
</head>
<body>
<c:if test="${sessionScope.userRole ne 'администратор' && sessionScope.userRole ne 'владелец'}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<h2 align="center" style="color:Black">Информация о авто</h2>

    <form action="controller" method="post">
        <input type="hidden" name="command" value="add_new_car">
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
                <td><input type="text" name="brand" value="${sessionScope.car.brand}"></td>
                <td><input type="text" name="model" value="${sessionScope.car.model}"></td>
                <td><input type="text" name="year" value="${sessionScope.car.year}"></td>
                <td><input type="text" name="mileage" value="${sessionScope.car.mileage}"></td>
                <td><input type="text" name="engineType" value="${sessionScope.car.engineType}"></td>
                <td><input type="text" name="engineSize" value="${sessionScope.car.engineSize}"></td>
                <td><input type="text" name="transmission" value="${sessionScope.car.transmissionType}"></td>
                <td><input type="text" name="wheelsDrive" value="${sessionScope.car.wheelsDrive}"></td>
                <td><input type="text" name="type" value="${sessionScope.car.type}"></td>
            </tr>
        </table>

        <h2 align="center" style="color:Black">Информация о прокате</h2>

        <table border="1" cellspacing="0" cellpadding="2">

            <tr>
                <th>Описание</th>
                <th>Стоимость</th>
                <th>Рейтинг</th>
                <th>Уровень топлива</th>
            </tr>

            <tr>
                <td><input type="text" name="description" value="${sessionScope.carRentInfo.description}"></td>
                <td><input type="text" name="cost" value="${sessionScope.carRentInfo.rentCost}"></td>
                <td><input type="text" name="rating" value="${sessionScope.carRentInfo.carRating}"></td>
                <td><input type="text" name="fuelLvl" value="${sessionScope.carRentInfo.fuelLvl}"></td>
            </tr>
        </table>
        <c:if test="${sessionScope.car == null}">
            <input type="submit" value="Добавить" />
        </c:if>
        <c:if test="${sessionScope.car != null}">
            <input type="submit" value="Изменить" />
        </c:if>
    </form>

</body>
</html>
