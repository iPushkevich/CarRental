<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<html>
<head>
    <title>All users</title>

</head>
<body>
<c:if test="${sessionScope.userRole != 'администратор' && sessionScope.userRole != 'владелец'}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<form action="controller" method="get">
    <input type="hidden" name="command" value="all_users">
    <p>
        <input type="radio" name="activeRents" value="activeRents" id="active">
        <label for="active"> С активными заказами </label>
    <p>
        <input type="radio" name="activeRents" value="all" id="all">
        <label for="all"> Все пользователи </label>
    <p>
        <input type="submit" value="Выбрать"></p>
</form>

<c:if test="${requestScope.user == null}">
    <c:if test="${requestScope.activeRents == null}">
        <h2 align="center" style="color:Black">Все пользователи</h2>
    </c:if>

    <c:if test="${requestScope.activeRents != null}">
        <h2 align="center" style="color:Black">Пользователи с активными заказами</h2>
    </c:if>

    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <th>eMail</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Телефон</th>
            <th>Подробнее</th>
        </tr>

        <c:forEach items="${requestScope.users}" var="user">
            <tr>
                <td>${user.value.email}</td>
                <td>${user.value.name}</td>
                <td>${user.value.surName}</td>
                <td>${user.value.phoneNumber}</td>
                <td>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="all_users">
                        <input type="hidden" name="userID" value="${user.key}">
                        <input type="submit" value="Подробнее">
                    </form>
                </td>

            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${requestScope.user != null}">
    <h2 align="center" style="color:Black">${requestScope.user.name} ${requestScope.user.surName}</h2>
    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <th>eMail</th>
            <th>Дата регистрации</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Возраст</th>
            <th>Телефон</th>
            <th>Серия и номер паспорта</th>
            <th>Опыт вождения</th>
            <th>Колличество ДТП</th>
            <th>Колличество заказов</th>
            <th>Скидка</th>
            <th>Права администратора</th>
        </tr>

        <tr>
            <td>${requestScope.user.email}</td>
            <td>${requestScope.regDate}</td>
            <td>${requestScope.user.name}</td>
            <td>${requestScope.user.surName}</td>
            <td>${requestScope.user.age}</td>
            <td>${requestScope.user.phoneNumber}</td>
            <td>${requestScope.user.passport}</td>
            <td>${requestScope.user.drivingExperience}</td>
            <td>${requestScope.user.roadAccidentCount}</td>
            <td>${requestScope.user.previousRentCount}</td>
            <td>${requestScope.user.discount}</td>
            <td>
                    ${requestScope.userRole}
                <c:if test="${sessionScope.userRole == 'владелец'}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="all_users">
                        <input type="hidden" name="userID" value="${requestScope.userID}">
                        <c:if test="${requestScope.userRole == 'пользователь'}">
                            <input type="hidden" name="newRole" value="admin">
                            <input type="submit" value="Назначить">
                        </c:if>
                        <c:if test="${requestScope.userRole == 'администратор'}">
                            <input type="hidden" name="newRole" value="user">
                            <input type="submit" value="Снять">
                        </c:if>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>

    <form action="controller" method="get">
        <input type="hidden" name="command" value="add_user_info">
        <input type="hidden" name="userIDFromAdmin" value="${requestScope.userID}">
        <input type="submit" value="Изменить">
    </form>
</c:if>

<h3 align="center" style="color:Black">${requestScope.noOrders}</h3>

<c:if test="${requestScope.orders != null}">
    <h2 align="center" style="color:Black">Заказы пользователя</h2>
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

<h2 align="center" style="color:Black">${sessionScope.dataChanged}</h2>
<%session.removeAttribute("dataChanged");%>

</body>
</html>
