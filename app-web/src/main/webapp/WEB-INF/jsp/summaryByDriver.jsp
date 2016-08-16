<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h1>Общий расход топлива водителем ${result.driver} составил</h1>

<table border="0">
    <tr/>
    <tr/>
    <tr/>
    <tr/>
    <tr>
        <td width="1000" align="center"><h1>${result.sum} л.</h1></td>
    </tr>
    <tr/>
    <tr/>
    <tr/>
</table>

<h2>В выборку вошли следующие рейсы:</h2>
<form:form method="get" modelAttribute="trips">
    <table>

        <td width="100"><spring:message code="trips.id"/></td>
        <td width="100"><spring:message code="trips.driver"/></td>
        <td width="100"><spring:message code="trips.startpoint"/></td>
        <td width="100"><spring:message code="trips.endpoint"/></td>
        <td width="100"><spring:message code="trips.startdate"/></td>
        <td width="100"><spring:message code="trips.enddate"/></td>

        <c:forEach var="trip" items="${result.trips}">
            <tr>
                <td width="100">${trip.id}</td>
                <td width="100">${trip.startPoint}</td>
                <td width="100">${trip.endPoint}</td>
                <td width="100">${trip.startDate}</td>
                <td width="100">${trip.startDate}</td>
            </tr>
        </c:forEach>
    </table>
</form:form>

<form action="/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>

