<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Simple jsp page</title>
</head>
<body>
<h1>
    <c:forEach items="${userList}" var="list">
        <span>${list.userId}   ${list.userName}</span> <br>
    </c:forEach>
    <span>${success}</span>
</h1>
<h1></h1>
</body>
</html>