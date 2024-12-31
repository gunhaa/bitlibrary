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
    <link rel="stylesheet" href="/resources/css/member/resetPw.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<form action="/member/resetPw" method="post" name="resetPwFrm" id="resetPwFrm">
		<div class="resetPw-logo">
			비밀번호 초기화
		</div>
		
		
		<div class="resetPw-input-area">
			<span>이메일</span>
			<input type="text" name="memberEmail" id="memberEmail">
			<button type="button" id="sendAuthKeyBtn">인증번호 받기</button>
		</div>
		
		<span class="resetPw-message" id="emailMessage"></span>
		
		
		<div class="resetPw-input-area">
			<span>인증번호</span>
			<input type="text" name="authKey" id="authKey">
			<button type="button" id="checkAuthKeyBtn">인증하기</button>
		</div>
		
		<span class="resetPw-message" id="authKeyMessage"></span>
		
		<div class="btn-area">
			<button>확인</button>
		</div>
	</form>
		
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script src="/resources/js/member/resetPw.js"></script>
</body>
</html>