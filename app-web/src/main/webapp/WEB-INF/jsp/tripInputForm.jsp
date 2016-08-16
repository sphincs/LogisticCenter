<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="trip.create"/></h1>

<form action="/submitTripData" method="post">
    <table border="0">
        <tr>
            <td><label path="driver"><spring:message code="trips.driver"/>:</label></td>
            <td><input type="text" name="DriverName" autofocus/><br/></td>
        </tr>
        <tr>
            <td><label path="car"><spring:message code="trips.car"/>:</label></td>
            <td><input type="text" name="Car"/><br/></td>
        </tr>
        <tr>
            <td><label path="car"><spring:message code="trips.fuelrate"/>:</label></td>
            <td><input type="text" name="FuelRate100"/><br/></td>
        </tr>
        <tr>
            <td><label path="startPoint"><spring:message code="trips.startpoint"/>:</label></td>
            <td><input type="text" name="StartPoint"/><br/></td>
        </tr>
        <tr>
            <td><label path="endPoint"><spring:message code="trips.endpoint"/>:</label></td>
            <td><input type="text" name="EndPoint"/><br/></td>
        </tr>
        <tr>
            <td><label path="distance"><spring:message code="trips.distance"/>:</label></td>
            <td><input type="text" name="Distance"/><br/></td>
        </tr>
        <tr>
            <td><label path="startDate"><spring:message code="trips.startdate"/>:</label></td>
            <td><input type="text" name="StartDate"/><br/></td>
            <td><spring:message code="trip.date.format"/></td>
        </tr>
        <tr>
            <td><label path="endDate"><spring:message code="trips.enddate"/>:</label></td>
            <td><input type="text" name="EndDate"/><br/></td>
            <td><spring:message code="trip.date.format"/></td>
        </tr>
        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.trip.add"/>></td>
        </tr>
    </table>
</form>

<form action="/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>
