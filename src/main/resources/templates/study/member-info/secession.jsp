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
	<link rel="stylesheet" href="/resources/css/my/info-style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<section class="container">
		<jsp:include page="/WEB-INF/views/my/sideMenu.jsp"/>

		<section class="myPage">

			<h1 class="myPage-title">회원탈퇴</h1>

			<form action="secession" method="POST" name="myPageFrm" id="secessionFrm">

				<div class="myPage-row">
					<label>비밀번호</label>
					<input type="password" name="memberPw" id="memberPw" maxlength="30">
				</div>
	
				<div class="myPage-row">
					<label>탈퇴확인</label>
					<input type="text" id="agree" placeholder='"탈퇴하겠습니다"를 입력해주세요'>
				</div>
	
				<button id="secession-btn" class="myPage-submit">탈퇴</button>

			</form>

		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/resources/js/my/page.js"></script>
</body>
</html>