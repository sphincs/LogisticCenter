<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="trips.update"/></h1>

<form action="/tripUpdate" method="post">
    <table border="0">
        <tr>
            <td><label path="id"><spring:message code="trips.id"/>:</label></td>
            <td><input type="text" name="Id" value="${trip.id}" readonly/><br/></td>
        </tr>
        <tr>
            <td><label path="driver"><spring:message code="trips.driver"/>:</label></td>
            <td><input type="text" name="DriverName" value="${trip.driverName}" autofocus/><br/></td>
        </tr>
        <tr>
            <td><label path="car"><spring:message code="trips.car"/>:</label></td>
            <td><input type="text" name="Car" value="${trip.car}"/><br/></td>
        </tr>
        <tr>
            <td><label path="car"><spring:message code="trips.fuelrate"/>:</label></td>
            <td><input type="text" name="FuelRate100" value="${trip.fuelRate100}"/><br/></td>
        </tr>
        <tr>
            <td><label path="startPoint"><spring:message code="trips.startpoint"/>:</label></td>
            <td><input type="text" name="StartPoint" value="${trip.startPoint}"/><br/></td>
        </tr>
        <tr>
            <td><label path="endPoint"><spring:message code="trips.endpoint"/>:</label></td>
            <td><input type="text" name="EndPoint" value="${trip.endPoint}"/><br/></td>
        </tr>
        <tr>
            <td><label path="distance"><spring:message code="trips.distance"/>:</label></td>
            <td><input type="text" name="Distance" value="${trip.distance}"/><br/></td>
        </tr>
        <tr>
            <td><label path="startDate"><spring:message code="trips.startdate"/>:</label></td>
            <td><input type="text" name="StartDate" value="${trip.startDate}"/><br/></td>
            <td><spring:message code="trip.date.format"/></td>
        </tr>
        <tr>
            <td><label path="endDate"><spring:message code="trips.enddate"/>:</label></td>
            <td><input type="text" name="EndDate" value="${trip.endDate}"/><br/></td>
            <td><spring:message code="trip.date.format"/></td>
        </tr>
        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.trip.update"/>></td>
        </tr>
    </table>
</form>

<form action="/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>
