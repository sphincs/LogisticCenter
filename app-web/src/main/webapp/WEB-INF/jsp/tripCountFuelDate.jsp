<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="trip.dates"/></h1>

<form action="/trips/tripCountFuelDate" method="get">
    <table border="0">
        <tr>
            <td><label path="startDate"><spring:message code="trips.after"/></label></td>
            <td><input type="text" name="StartDate" autofocus/><br/></td>
            <td><spring:message code="trip.date.format"/></td>
        </tr>
        <tr>
            <td><label path="endDate"><spring:message code="trips.before"/></label></td>
            <td><input type="text" name="EndDate" autofocus/><br/></td>
            <td><spring:message code="trip.date.format"/></td>
        </tr>
        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.trip.count"/>></td>
        </tr>
    </table>
</form>

<form action="/trips/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>

