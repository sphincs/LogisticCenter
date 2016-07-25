<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<body>

<style type="text/css">
    TABLE {
        width: 300px;
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
    <h1><spring:message code="drivers.list" /></h1>
    <ul>
        <table>
            <th>
            <td>driverid</td>
            <td>drivername</td>
            <td>age</td>
            <td>category</td>
            <td>car</td>
            <td>carnumber</td>
            <td>fuelrate</td>
            </th>
            <c:forEach items="${driver}" var="driver">
                <tr>
                    <td/>
                    <td>${driver.driverid}</td>
                    <td>${driver.drivername}</td>
                    <td>${driver.age}</td>
                    <td>${driver.category}</td>
                    <td>${driver.car}</td>
                    <td>${driver.carnumber}</td>
                    <td>${driver.fuelrate}</td>
                </tr>
            </c:forEach>
        </table>
    </ul>
</form:form>

<a href='<spring:url value="/inputForm" />'> <spring:message code="driver.create" /></a>
</body>
</html>