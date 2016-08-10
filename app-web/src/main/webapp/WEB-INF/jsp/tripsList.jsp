<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<body>

<style type="text/css">
    TABLE {
        border-collapse: collapse;
    }

    TD, TH {
        padding: 3px;
        border: 1px solid black;
    }

    TH {
        background: #b0e0e6;
    }
</style>


<form:form method="get" modelAttribute="trips">
    <h1><spring:message code="trips.list"/></h1>
    <ul>
        <table>
            <th>
            <td><spring:message code="trips.id"/></td>
            <td><spring:message code="trips.driver"/></td>
            <td><spring:message code="trips.car"/></td>
            <td><spring:message code="trips.fuelrate"/></td>
            <td><spring:message code="trips.startpoint"/></td>
            <td><spring:message code="trips.endpoint"/></td>
            <td><spring:message code="trips.distance"/></td>
            <td><spring:message code="trips.startdate"/></td>
            <td><spring:message code="trips.enddate"/></td>
            <td><spring:message code="trips.sumfuel"/></td>
            </th>
            <c:forEach var="trip" items="${trips}">
                <tr>
                    <td/>
                    <td>${trip.id}</td>
                    <td>${trip.driverName}</td>
                    <td>${trip.car}</td>
                    <td>${trip.fuelRate100}</td>
                    <td>${trip.startPoint}</td>
                    <td>${trip.endPoint}</td>
                    <td>${trip.distance}</td>
                    <td>${trip.startDate}</td>
                    <td>${trip.endDate}</td>
                    <td>${trip.sumFuel}</td>
                </tr>
                <tr/>
            </c:forEach>
        </table>
    </ul>
</form:form>


<form action="/trips/tripInputForm" method="get">
    <input type="submit" name="Submit" width="2000" value=<spring:message code="button.trip.create"/>>
</form>
<form action="/trips/tripUpdateId" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.trip.update"/>>
</form>
<form action="/trips/tripRemoveForm" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.trip.remove"/>>
</form>
<form action="/trips/tripCountFuelDateForm" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.trip.countfueldate"/>>
</form>
<form action="/trips/tripCountFuelDriverForm" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.trip.countfueldriver"/>>
</form>
<form action="/" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.startpage"/>>
</form>


</body>
</html>