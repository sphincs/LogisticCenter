<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="trips.driverchoose"/></h1>

<form action="/trips/tripCountFuelDriver" method="get">
    <table border="0">
        <tr>
            <td><label path="driver"><spring:message code="driver.id"/></label></td>
            <td><input type="text" name="DriverId" autofocus/><br/></td>
        </tr>
        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.trip.count"/>></td>
        </tr>
    </table>
</form>

<form:form method="get" modelAttribute="drivers">
    <table>
        <tr><spring:message code="drivers.available"/></tr>

        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td width="200">${driver.id}</td>
                <td width="200">${driver.name}</td>
            </tr>
        </c:forEach>
    </table>
</form:form>

<form action="/trips/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>