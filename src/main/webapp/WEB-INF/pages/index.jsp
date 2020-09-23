<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/begin" method="get">
   Привет, давай начнем играть!
    ${city}
</form>
<form action="/next" method="get">
    ${notMatchLetters}
    ${notFoundCityError}
    ${notExistCity}
    <input type="text" maxlength="25" size="40" name="city"></h4>
        <input type="submit" value="Подтвердить">
</form>
<form action="/end" method="post">
    <input type="submit" value="Закончить игру">
</form>
</body>
</html>
