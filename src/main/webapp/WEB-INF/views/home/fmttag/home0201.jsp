<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home </title>
</head>
<body>
	<p>1) type 속성을 지정하거나 pattern 속성을 지정하여 숫자를 형식화한다.</p>
	<p>coin : ${coin }</p>
	<%-- <p><fmt:parseNumber value="${coin }" var="coinNum" /></p> --%> 	 
	<%-- <p><fmt:parseNumber value="${coin }" var="coinNum" type="percent"/></p>  --%>
	<p><fmt:parseNumber value="${coin }" var="coinNum" type="currency"/></p>
	<p>coinNum : ${coinNum }</p>	
</body>
</html>