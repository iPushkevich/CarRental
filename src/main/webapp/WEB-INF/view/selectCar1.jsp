<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Select a car</title>
</head>
<body>

<jsp:include page="header.jsp"/>

<c:if test="${requestScope == null}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<c:if test="${requestScope.model == null} || ${requestScope.brand == null}">
    <form action="controller" method="get">
        <input type="hidden" name="command" value="select_car">

        <c:forEach var="brand" items="${requestScope.brands}">
            <h1>
                <input type="submit" name="brand" value="${brand}">
            </h1>
        </c:forEach>
    </form>
</c:if>

<c:if test="${requestScope.model == null}">

    <h2 align="center" style="color:Black">Все ${requestScope.brand}</h2>
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
            <th>Заказать</th>
        </tr>

        <c:forEach items="${requestScope.carsByBrand}" var="car">
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
    </table>


</c:if>
<div class="container-sm text-center">
    <form role="form" action="controller" method="get">
        <input type="hidden" name="command" value="select_car">

        <strong><h3>Выберите модель</h3></strong>
            <label>
                <select class="form-control" name="model">
                    <c:forEach var="model" items="${requestScope.models}">
                        <option value="${model}"> ${model} </option>
                    </c:forEach>
                </select>
            </label>
            <input type="hidden" name="brand" value="${requestScope.brand}">
            <input type="submit" value="Выбрать">
    </form>

    <c:if test="${requestScope.model != null}">

        <c:if test="${requestScope.availableCars == null}">
            <h2 align="center" style="color:Black">Все ${requestScope.brand} ${requestScope.model}</h2>
        </c:if>

        <c:if test="${requestScope.availableCars != null}">
            <h2 align="center" style="color:Black">Доступные ${requestScope.brand} ${requestScope.model}</h2>
        </c:if>

        <c:if test="${requestScope.availableCars == null}">
            <form name="controller" method="get">
                <input type="hidden" name="command" value="select_car">
                <input type="hidden" name="isAvailable" value="1">
                <input type="hidden" name="model" value="${requestScope.model}">
                <input type="hidden" name="brand" value="${requestScope.brand}">
                <input type="submit" value="Только доступные">
            </form>
        </c:if>

        <c:if test="${requestScope.availableCars != null}">
            <form name="controller" method="get">
                <input type="hidden" name="command" value="select_car">
                <input type="hidden" name="model" value="${requestScope.model}">
                <input type="hidden" name="brand" value="${requestScope.brand}">
                <input type="submit" value="Все">
            </form>
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
                <th>Подробнее</th>
            </tr>


            <c:forEach items="${requestScope.carsByModel}" var="car">
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
        </table>
        <br>

        <h3>Другие марки</h3>

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
    </c:if>
</div>

</body>
</html>
