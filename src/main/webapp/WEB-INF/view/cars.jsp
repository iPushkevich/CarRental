<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<html>
<head>
    <title>Cars</title>
</head>
<body>
<c:if test="${sessionScope.userRole != 'администратор' && sessionScope.userRole != 'владелец'}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

 <h2 align="center" style="color:Black">Все авто</h2>
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
        </tr>
    </c:forEach>
    </table>

        <br>

        <h2><a href="controller?command=go_to_new_car_page">Добавить авто</a></h2>

        <br>

        <h2><c:out value="${sessionScope.carAdded}"/></h2>
        ${sessionScope.remove(sessionScope.carAdded)}

</body>
</html>
