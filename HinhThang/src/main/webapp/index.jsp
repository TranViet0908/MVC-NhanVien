<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tính Diện Tích Hình Thang</title>
</head>
<body>
    <h1>Tính Diện Tích Hình Thang</h1>
    <form action="calculate" method="post">
        <label for="base1">Đáy thứ nhất (a):</label>
        <input type="number" id="base1" name="base1" required><br><br>

        <label for="base2">Đáy thứ hai (b):</label>
        <input type="number" id="base2" name="base2" required><br><br>

        <label for="height">Chiều cao (h):</label>
        <input type="number" id="height" name="height" required><br><br>

        <input type="submit" value="Tính diện tích">
    </form>

    <c:if test="${not empty area}">
        <h2>Diện tích hình thang là: ${area}</h2>
    </c:if>
</body>
</html>
