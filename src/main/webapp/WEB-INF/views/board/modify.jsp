<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modify</title>
</head>
<body>
	<h3>modify</h3>
	
	
	<form action="/board/post" method="post">
		<input type="submit" name="modify" value="Modify"/> 
	</form>
	
	<a href="/board/get?list">List</a>
</body>
</html>