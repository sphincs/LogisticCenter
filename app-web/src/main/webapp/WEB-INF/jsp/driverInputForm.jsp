<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="driver.create"/></h1>

<form action="/drivers/submitData" method="post">
    <table border="0">
        <tr>
            <td><label path="name"><spring:message code="driver.name"/>:</label></td>
            <td><input type="text" name="Name" autofocus/><br/></td>
        </tr>
        <tr>
            <td><label path="age"><spring:message code="driver.age"/>:</label></td>
            <td><input type="text" name="Age"/><br/></td>
        </tr>
        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.driver.add"/>></td>
        </tr>
    </table>
</form>

<form action="/drivers/driversList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>
