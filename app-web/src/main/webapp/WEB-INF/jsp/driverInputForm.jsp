<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <body>
        <h1><spring:message code="driver.create" /> </h1>

        <form action="/submitData" method="post">
            <table border="0">
                <tr>
                    <td><label path="driverName"><spring:message code="driver.name" />:</label></td><td><input type="text" name="Name" /><br/></td>
                </tr>
                <tr>
                    <td><label path="driverAge"><spring:message code="driver.age" />:</label></td><td><input type="text" name="Age" /><br/></td>
                </tr>
                <tr>
                    <td><label path="driverCategory"><spring:message code="driver.category" />:</label></td><td><input type="text" name="Category" /><br/></td>
                </tr>
                <tr>
                    <td><label path="driverCar"><spring:message code="driver.car" />:</label></td><td><input type="text" name="Car" /><br/></td>
                </tr>
                <tr>
                    <td><label path="driverCarNumber"><spring:message code="driver.number" />:</label></td><td><input type="text" name="Number" /><br/></td>
                </tr>
                <tr>
                    <td><input type="submit" name="Submit" value=<spring:message code="driver.add" />></td>
                </tr>
            </table>
        </form>

    </body>
</html>
