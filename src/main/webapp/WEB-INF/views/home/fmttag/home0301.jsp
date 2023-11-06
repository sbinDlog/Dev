<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<p>1) type 속성을 지정하거나 pattern 속성을 지정하여 숫자를 형식화한다.</p>
	<p>now : ${now }</p>
	<p>date default : <fmt:formatDate value="${now }" type="date" dateStyle="default"/> </p>
	<p>date short : <fmt:formatDate value="${now }" type="date" dateStyle="short"/> </p>
	<p>date medium : <fmt:formatDate value="${now }" type="date" dateStyle="medium"/> </p>
	<p>date long : <fmt:formatDate value="${now }" type="date" dateStyle="long"/> </p>
	<p>date full : <fmt:formatDate value="${now }" type="date" dateStyle="full"/> </p>
	
	<!--
		dateStyle은 총 5가지로 각 스타일에 따라 출력 형태가 정해져있다.
		정해진 출력 형태에 따라서 parsing할 때도 해당 형식으로 구성된 문자열이라면 같은 스타일을 가진 
		Date타입 형태의 데이터로 파싱이 가능하다.
	  -->
</body>
</html>