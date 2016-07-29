<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="driver.update"/></h1>

<form action="/drivers/driverUpdate" method="post">
    <table border="0">
        <tr>
            <td><label path="id"><spring:message code="driver.id"/>:</label></td>
            <td><input type="text" name="Id" value="${driver.id}" readonly/><br/></td>
        </tr>
        <tr>
            <td><label path="name"><spring:message code="driver.name"/>:</label></td>
            <td><input type="text" name="Name" value="${driver.name}"/><br/></td>
        </tr>
        <tr>
            <td><label path="age"><spring:message code="driver.age"/>:</label></td>
            <td><input type="text" name="Age" value="${driver.age}"/><br/></td>
        </tr>
        <tr>
            <td><label path="categories"><spring:message code="driver.category"/>:</label></td>
            <td><input type="text" name="Category" value="${driver.categories}"/><br/></td>
            <td><spring:message code="driver.category.format"/></td>
        </tr>
        <tr>
            <td><label path="car"><spring:message code="driver.car"/>:</label></td>
            <td><input type="text" name="Car" value="${driver.car}"/><br/></td>
        </tr>
        <tr>
            <td><label path="carNumber"><spring:message code="driver.number"/>:</label></td>
            <td><input type="text" name="Number" value="${driver.carNumber}"/><br/></td>
        </tr>
        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.driver.update"/>></td>
        </tr>
    </table>
</form>

<form action="/drivers/driversList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>
