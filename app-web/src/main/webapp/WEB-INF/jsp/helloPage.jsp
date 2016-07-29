<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logistic Center Manager</title>
</head>
<body>
<h1><spring:message code="hello"/></h1>
<table border="0">
    <tr>
        <td width="30">
            <form action="/drivers/" method="get">
                <input type="submit" name="Submit" value=<spring:message code="button.driver.goto"/>>
            </form>
        </td>
        <td width="30">
            <form action="/trips/" method="get">
                <input type="submit" name="Submit" value=<spring:message code="button.trip.goto"/>>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
