<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Simple jsp page</title>
</head>
<body>
<h1>
    <c:if test="${!empty userList}">
        <c:forEach items="${userList}" var="list">
            <span>${list.userId}   ${list.userName}</span> <br>
        </c:forEach>
        <span>${success}</span>
    </c:if>

    <div>你好，欢迎来到springboot!!</div>
    <form action="imgUpload" method="post" enctype="multipart/form-data">
        请选择图片：<input type="file" name="uploadFile">
        <input type="submit" value="上传图片">
    </form>

</h1>
<h1></h1>
</body>
</html>