<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>게시판 목록</title>
</head>
<body>
<div>
	<form action="/board/insertBoard" method = "get">	
		<p>제목<input type = "text" name ="title"></p>
		<p>내용<textarea name = "contents"></textarea></p>
		<input type ="submit" value = "글쓰기">
	</form>
	
</div>
<div>

</div>


</body>
</html>
