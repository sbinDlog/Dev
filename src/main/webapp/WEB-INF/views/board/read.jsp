<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>read</title>
</head>
<body>
<!-- 상세보기 -->
	<h3>read</h3>
	
	<form action="/board/get" method="get">
		<input type="submit" name="modify" value="Modify"> 
		<input type="submit" name="remove" value="Remove"> 
	</form>
	
	<a href="/board/get?list">List</a>
</body>
</html>