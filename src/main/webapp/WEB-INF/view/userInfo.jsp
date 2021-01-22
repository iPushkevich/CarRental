<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<html>
<head>
    <title>USER INFO</title>
</head>
<body>
<c:if test="${sessionScope.userRole == null}">
    <c:redirect url="controller?command=go_to_main_page"/>
</c:if>

<c:if test="${sessionScope.userRole == 'гость'}">
<h3>${sessionScope.success}</h3>
</c:if>
<br>

<form action="controller" method="post">
    <input type="hidden" name="command" value="add_user_info">
    <c:if test="${requestScope.adminAction != null}">
        <input type="hidden" name="adminAction" value="1">
    </c:if>
    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Возраст</th>
            <th>Телефон</th>
            <th>Серия и номер паспорта</th>
            <th>Стаж вождения</th>
            <th>Колличество ДТП</th>
            <c:if test="${sessionScope.user != null}">
                <th>Предыдущие заказы</th>
                <th>Скидка</th>
            </c:if>
        </tr>
        <tr>
            <td><input type="text" name="name" required="required" value="${sessionScope.user.name}"></td>
            <td><input type="text" name="surName" required="required" value="${sessionScope.user.surName}"></td>
            <td><input type="text" name="age" required="required" value="${sessionScope.user.age}"></td>
            <td><input type="text" name="phoneNumber" required="required" minlength="9" maxlength="9" value="${sessionScope.user.phoneNumber}"></td>
            <td><input type="text" name="passport" required="required" minlength="9" maxlength="9" value="${sessionScope.user.passport}"></td>
            <td><input type="text" name="experience" required = "required" value="${sessionScope.user.drivingExperience}"></td>
            <td><input type="text" name="accident" required = "required" value="${sessionScope.user.roadAccidentCount}"></td>
            <c:if test="${sessionScope.user != null}">
                <td>${sessionScope.user.previousRentCount}</td>
                <td>${sessionScope.user.discount}</td>
            </c:if>
        </tr>
    </table>
    <br>
    <input type="submit" value="Готово"/>

</form>
<br>
<c:if test="${sessionScope.userRole == 'администратор' || sessionScope.userRole == 'владелец'}">
    <h3><a href="controller?command=all_users">Все пользователи</a> </h3>
</c:if>

</body>
</html>
