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

			<h1 class="myPage-title">비밀번호 변경</h1>

			<form action="changePw" method="POST" name="myPageFrm" id="changePwFrm">

				<div class="myPage-row">
					<label>현재 비밀번호</label> <input type="password" name="currentPw" id="currentPw" maxlength="30">
				</div>

				<div class="myPage-row">
					<label>새 비밀번호</label> <input type="password" name="newPw" id="newPw" maxlength="30" placeholder="8~20자의 영문, 숫자, 특수문자를 조합하여 입력해주세요.">
				</div>

				<div class="myPage-row">
					<label>새 비밀번호 확인</label> <input type="password" name="newPwConfirm" id="newPwConfirm" maxlength="30">
				</div>

				<button class="myPage-submit">변경하기</button>

			</form>

		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/resources/js/my/page.js"></script>
</body>
</html>