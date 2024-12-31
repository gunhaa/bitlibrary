<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library</title>
    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/member/findId.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<form action="/member/findId" method="post" name="findIdFrm" id="findIdFrm">
		<div class="findId-logo">
			아이디 찾기
		</div>
		
		
		<div class="findId-input-area">
			<span>이름</span>
			<input type="text" name="memberName" id="memberName" placeholder="이름을 입력해주세요">
		</div>
		
		<div class="findId-input-area">
			<span>전화번호</span>
			<input type="text" name="memberPhoneNo" id="memberPhoneNo" placeholder="(-)없이 번호를 입력해주세요">
		</div>
		
		<div class="btn-area">
			<button>확인</button>
		</div>
	</form>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>