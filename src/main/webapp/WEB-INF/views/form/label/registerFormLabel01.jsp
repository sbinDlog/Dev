<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring Form Tag</title>
</head>
<body>
	<h2>Spring Form Tag</h2>
	<form:form method="post" action="/formtag/label/result" modelAttribute="member">
	<form:hidden path="userId"/>
	<table>
		<tr>
			<td> <form:label path="userId">유저ID </form:label><td>
			<td>
				<form:input path="userId"/>
				<font color="red">
					<form:errors path="userId"/>
				</font>
			</td>
		</tr>
		<tr>
			<td> <form:label path="userName">이름 </form:label><td>
			<td>
				<form:input path="userName"/>
				<font color="red">
					<form:errors path="userName"/>
				</font>
			</td>
		</tr>
	</table>
	<form:button name="register">등록</form:button>
	</form:form>
</body>
</html>