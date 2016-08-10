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


<form:form method="get" modelAttribute="drivers">
    <h1><spring:message code="drivers.list"/></h1>
    <ul>
        <table>
            <th>
            <td><spring:message code="driver.id"/></td>
            <td><spring:message code="driver.name"/></td>
            <td><spring:message code="driver.age"/></td>
            </th>
            <c:forEach var="driver" items="${drivers}">
                <tr>
                    <td/>
                    <td>${driver.id}</td>
                    <td>${driver.name}</td>
                    <td>${driver.age}</td>
                </tr>
                <tr/>
            </c:forEach>
        </table>
    </ul>
</form:form>


<form action="/drivers/driverInputForm" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.driver.create"/>>
</form>
<form action="/drivers/driverUpdateId" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.driver.update"/>>
</form>
<form action="/drivers/driverRemoveForm" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.driver.remove"/>>
</form>
<form action="/" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.startpage"/>>
</form>


</body>
</html>