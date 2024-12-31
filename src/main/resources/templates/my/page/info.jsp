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

			<h1 class="myPage-title">내 정보</h1>

			<form action="info" method="POST" name="myPageFrm" id="updateFrm">

				<div class="myPage-row">
					<label>이름</label>
					<input type="text" name="memberName" id="memberName" value=${loginMember.memberName}>
				</div>

				<div class="myPage-row">
					<label>전화번호</label>
					<input type="text" name="memberPhoneNo" id="memberPhoneNo" value=${loginMember.memberPhoneNo}>
				</div>

				<div class="myPage-row info-title">
					<span>주소</span>
				</div>

				<c:set var="addr" value="${fn:split(loginMember.memberAddress,'^^^')}" />

				<div class="myPage-row info-address">
					<input type="text" name="memberAddress" id="sample6_postcode" placeholder="우편번호" value="${addr[0]}">
					<button type="button" onclick="sample6_execDaumPostcode()">검색</button>
				</div>

				<div class="myPage-row info-address">
					<input type="text" name="memberAddress" id="sample6_address" placeholder="도로명/지번 주소" value="${addr[1]}">
				</div>

				<div class="myPage-row info-address">
					<input type="text" name="memberAddress" id="sample6_detailAddress" placeholder="상세 주소" value="${addr[2]}">
				</div>

				<button class="myPage-submit">수정하기</button>

			</form>

		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        function sample6_execDaumPostcode() {
            var width = 500;
            var height = 500;
            new daum.Postcode({
                width: width,
                height: height,
                oncomplete: function(data) {
                    var addr = '';
                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;
                    } else {
                        addr = data.jibunAddress;
                    }  

                    document.getElementById('sample6_postcode').value = data.zonecode;
                    document.getElementById("sample6_address").value = addr;
                    document.getElementById("sample6_detailAddress").focus();
                }
            }).open({
                left: (window.screen.width / 2) - (width / 2),
                top: (window.screen.height / 2) - (height / 2)
            });
        }
    </script>
	<script src="/resources/js/my/page.js"></script>
</body>
</html>