<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>second</title>
</head>
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

<form:form method="get" modelAttribute="driver">
    <h1>Водитель</h1>
    <ul>
        <table>
            <th>
            <td>ID</td>
            <td>Имя</td>
            <td>Возраст</td>
            <td>Категории</td>
            <td>Автомобиль</td>
            <td>Номер автомобиля</td>
            <td>Расход топлива</td>
            </th>

            <tr>
                <td/>
                <td>${driver.id}</td>
                <td>${driver.name}</td>
                <td>${driver.age}</td>
                <td>${driver.categories}</td>
                <td>${driver.car}</td>
                <td>${driver.carNumber}</td>
                <td>${driver.fuelRate100}</td>
            </tr>

        </table>
    </ul>
</form:form>

</body>
</html>
