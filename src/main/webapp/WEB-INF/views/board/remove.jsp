<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>remove</title>
</head>
<body>
	<h3>remove</h3>

	<!--params와 일치되는 컨트롤러 메서드가 실행됨  -->	
	<form action="/board/post" method="post">
		<input type="submit" name="remove" value="Remove"> 
	</form>
	
	<a href="/board/get?list">List</a>
</body>
</html>