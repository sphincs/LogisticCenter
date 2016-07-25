<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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


<form action="/trips/tripsList" method="get">
    <input type="submit" name="Submit" value=<spring:message code="button.back"/>>
</form>
</body>
</html>

