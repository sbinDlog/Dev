<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register All Result</title>
</head>
<body>
<!-- 
	[아래 결과처럼 출력해주세요.]
	
	유저ID			a001
	패스워드			1234
	이름				조현준
	E-MAIL			wh-guswns123@hanmail.net
	생년월일			2023년 08월 25일
	성별				남자
	개발자여부			개발자
	외국인여부			외국인
	국적				대한민국
	소유차량			소유차량 없음
	취미				운동 영화시청
	우편번호			45617
	주소				대전광역시 서구  오류동
	카드1(번호)		1451-1234-1234-1122
	카드1(유효년월)	2040년 09월 09일
	카드2(번호)		1234-1234-1234-1234
	카드2(유효년월)	2040년 09월 09일
	소개				반갑습니다
	
	** 조건
	- 선택한 성별에 따라 남자/여자/기타로 나타내주세요.
	- 개발자 여부 체크에 따라 개발자/일반으로 나타내주세요.
	- 외국인 여부 체크에 따라 외국인/내국인으로 나타내주세요.
	- 국적에 따라 대한민국/독일/호주/캐나다로 나타내주세요.
	- 소유차량 선택에 따라 소유차량 없음/JEEP/VOLVO/BMW/AUDI로 나타내주세요.
		선택 갯수에 따라 아래처럼 출력해주세요.
		> 소유차량 없음
		> JEEP
		> JEEP VOLVO AUDI
	- 취미 선택에 따라 운동/영화시청/음악감상로 나타내주세요.
		선택 갯수에 따라 아래처럼 출력해주세요.
		> 취미 없음
		> 음악감상
		> 운동 영화시청

 -->
	<c:set value="${member.getUserId() }" var="userId"/>
	<c:out value="유저 ID : ${userId}"/><br/>
	
	<c:out value="패스워드 : ${member.getPassword() }"/><br/>
	
	<c:set value="${member.getUserName() }" var="userName"/>
	<c:out value="이름 : ${userName}"/><br/>
	
	<c:set value="${member.getEmail() }" var="email"/>
	<c:out value="이메일 : ${email}"/><br/>
	
	생년월일 : <fmt:formatDate value="${member.getDateOfBirth() }" type="date" pattern="yyyy년MM월dd일"/> 
	<br/>
	
	<c:set value="${member.getGender() }" var="gender"/>
	<c:choose>
		<c:when test="${gender eq 'male'}">
			<c:out value="성별 : 남자"/><br/>
		</c:when>
		<c:when test="${gender eq 'female'}">
			<c:out value="성별 : 여자"/><br/>
		</c:when>
		<c:otherwise>
			<c:out value="성별 : 기타"/><br/>
		</c:otherwise>
	</c:choose>
	
	<!-- 선생님질문!!! -->
	개발자 여부 : ${member.developer }<br/>
	
	
	<c:set value="${member.isForeigner()}" var="foreigner"/>	
	<c:choose>
		<c:when test="${foreigner == true}">
			<c:out value="외국인 여부 : 외국인"/><br/>
		</c:when>
		<c:otherwise>
			<c:out value="외국인 여부 : 내국인"/><br/>
		</c:otherwise>
	</c:choose>

	<c:set value="${member.getNationality()}" var="nationality"/>
	<c:choose>
		<c:when test="${nationality == 'Korea'}">
			<c:out value="국적 : 대한민국"/><br/>
		</c:when>
		<c:when test="${nationality == 'Germany'}">
			<c:out value="국적 : 독일"/><br/>
		</c:when>
		<c:when test="${nationality == 'Austrailia'}">
			<c:out value="국적 : 호주"/><br/>
		</c:when>
		<c:otherwise>
			<c:out value="국적  : 캐나다"/><br/>
		</c:otherwise>
	</c:choose>

	<c:set value="${member.getCars()}" var="cars"/>	
	<c:choose>
		<c:when test="${cars eq ''}">
			<c:out value="소유차량 : 없음"/><br/>
		</c:when>
		<c:otherwise>
			<c:out value="소유차량 : ${cars }"/><br/>
		</c:otherwise>
	</c:choose>
	
	
	<c:out value="취미 : ${member.getHobby() }"/><br/>
	
	
	<c:out value="우편 번호  : ${address.getPostCode() }"/><br/>
	<c:out value="주소  : ${address.getLocation() }"/><br/>
	
	카드1(번호):<c:out value="${member.cardList[0].no }"/><br/>
	카드1(유효년월):<fmt:formatDate value="${member.cardList[0].validMonth }" type="date" pattern="yyyy년 MM월 dd일"/><br/>
	
	카드2(번호):<c:out value="${member.cardList[1].no }"/><br/>
	카드2(유효년월):<fmt:formatDate value="${member.cardList[1].validMonth }" type="date" pattern="yyyy년 MM월 dd일"/><br/>
	
	<c:out value="소개  : ${member.getIntroduction() }"/>
</body>
</html>