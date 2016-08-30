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
            <form action="/onepage/" method="get">
                <input type="submit" name="Submit" value=<spring:message code="button.onepage.goto"/>>
            </form>
        </td>
        <td width="30">
            <form action="/jsp/" method="get">
                <input type="submit" name="Submit" value=<spring:message code="button.jsp.goto"/>>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
