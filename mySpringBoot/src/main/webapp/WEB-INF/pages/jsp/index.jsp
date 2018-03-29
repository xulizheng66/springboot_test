<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Simple jsp page</title>
</head>
<body>
<h1>
    <c:forEach items="${userList}" var="list">
        <h2> ${list.userId}   ${list.userName}</h2></br>
    </c:forEach>
</h1>

</body>
</html>