<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring Form Tag</title>
</head>
<body>
<!-- 맵은 키에 들어간게 01 라벨에 들어간게 스포츠 -->
	<h2>Spring Form Tag</h2>
	<p>2) 모델에 List 타입의 데이터를 생성하여 추가한 후에 화면에 전달한다.</p>
	<form:form action="/formtag/register" method="post" modelAttribute="member">
	<table>
		<tr>
			<td>취미(hobbyList)</td>
			<td>
				<form:checkboxes items="${hobbyCodeList}" path="hobbyList" itemLabel="label" itemValue="value"/>
			</td>
		</tr>
	</table>
	<form:button name="register">등록</form:button>
	</form:form>
</body>
</html>