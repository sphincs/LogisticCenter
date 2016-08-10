<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="driver.update"/></h1>

<form action="/drivers/driverUpdateForm" method="post">
    <table border="0">
        <tr>
            <td><label path="id"><spring:message code="driver.id"/>:</label></td>
            <td><input type="text" name="Id" autofocus/><br/></td>
        </tr>

        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.driver.update"/>></td>
        </tr>
    </table>
</form>

<form:form method="get" modelAttribute="drivers">
    <table>
        <tr><spring:message code="drivers.available"/></tr>
        <td width="100"><spring:message code="driver.id"/></td>
        <td width="100"><spring:message code="driver.name"/></td>

        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td width="100">${driver.id}</td>
                <td width="100">${driver.name}</td>
            </tr>
        </c:forEach>
    </table>
</form:form>


<form action="/drivers/driversList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>