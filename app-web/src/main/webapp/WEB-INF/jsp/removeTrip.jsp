<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1><spring:message code="trip.remove"/></h1>

<form action="/trips/tripRemove" method="post">
    <table border="0">
        <tr>
            <td><label path="id"><spring:message code="trips.id"/>:</label></td>
            <td><input type="text" name="Id" autofocus/><br/></td>
        </tr>

        <tr>
            <td><input type="submit" name="Submit" value=<spring:message code="button.trip.remove"/>></td>
        </tr>
    </table>
</form>

<form:form method="get" modelAttribute="trips">
    <table>
        <tr><spring:message code="trips.available"/></tr>

        <c:forEach var="trip" items="${trips}">
            <tr>
                <td width="200">${trip.id}</td>
                <td width="200">${trip.startPoint}</td>
                <td width="200">${trip.endPoint}</td>
                <td width="200">${trip.startDate}</td>
                <td width="200">${trip.startDate}</td>
            </tr>
        </c:forEach>
    </table>
</form:form>

<form action="/trips/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>
