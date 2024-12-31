<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library</title>
<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/member/signUp.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

	<section class="signUp-content">
		<form action="/member/signUp" name="signUp-form" id="signUpFrm" method="post">
			<h3>회원 정보 입력</h3>

			<label for="memberEmail">
				<span class="required">*</span> 아이디(이메일)
			</label>
			
			<div class="signUp-input-area">
				<input type="text" id="memberEmail" name="memberEmail" placeholder="이메일" maxlength="30" autocomplete="off">

				<button type="button" id="sendAuthKeyBtn">인증번호 받기</button>
			</div>
			
			<span class="signUp-message" id="emailMessage">메일을 받을 수 있는 이메일을 입력해주세요.</span>
			
               <label for="emailCheck">
                   <span class="required">*</span> 인증번호
               </label>

               <div class="signUp-input-area">
                   <input type="text" name="authKey" id="authKey" s placeholder="인증번호 입력" maxlength="6" autocomplete="off" >
                  
                   <button id="checkAuthKeyBtn" type="button">인증하기</button>
               </div>
               
               <span class="signUp-message" id="authKeyMessage"></span>
			
			<label for="memberPw">
				<span class="required">*</span> 비밀번호
			</label>
			
			<div class="signUp-input-area">
				<input type="password" id="memberPw" name="memberPw" placeholder="비밀번호" maxlength="30">
			</div>

			<div class="signUp-input-area">
				<input type="password" id="memberPwConfirm" placeholder="비밀번호확인" maxlength="30">
			</div>

			<span class="signUp-message" id="pwMessage">8~20자의 영문, 숫자, 특수문자를 조합하여 입력해주세요.</span>
			
			<label for="memberName">
				<span class="required">*</span> 이름
			</label>
			
			<div class="signUp-input-area">
				<input type="text" id="memberName" name="memberName" placeholder="이름(실명)" maxlength="10">
			</div>

			<span class="signUp-message" id="nameMessage"></span>
			
			<label>
				<span class="required">*</span> 성별
			</label>
			
			<div class="signUp-input-gender-area" id="memberGender">
				<label><input type="radio" name="memberGender" value="M">남자</label>
				<label><input type="radio" name="memberGender" value="F">여자</label>
			</div>
			
			<label for="memberDOB">
				<span class="required">*</span> 생년월일
			</label>
			
			<div class="signUp-input-area">
				<input type="text" id="memberDateOfBirth" name="memberDateOfBirth" placeholder="숫자 8자리 (ex. 19980909)" maxlength="8">
			</div>
			
			<span class="signUp-message" id="dobMessage"></span>

			<label for="memberPhonoNo">
				<span class="required">*</span> 휴대폰번호
			</label>

			<div class="signUp-input-area">
				<input type="text" id="memberPhoneNo" name="memberPhoneNo" placeholder="(-없이 숫자만 입력)" maxlength="11">
			</div>

			<span class="signUp-message" id="pnoMessage"></span>
			
			<label for="memberAddress">주소</label>


               <div class="signUp-input-area">
                   <input type="text" name="memberAddress" id="sample6_postcode" placeholder="우편번호" maxlength="6">
                  
                   <button type="button" onclick="sample6_execDaumPostcode()">검색</button>
               </div>


               <div class="signUp-input-area">
                   <input type="text" name="memberAddress" id="sample6_address" placeholder="도로명/지번 주소">
               </div>


               <div class="signUp-input-area">
                   <input type="text" name="memberAddress" id="sample6_detailAddress" placeholder="상세 주소">
               </div>

			<button type="submit" id="signUp-btn">가입하기</button>
		</form>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script src="/resources/js/member/signUp.js"></script>

	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        function sample6_execDaumPostcode() {
            var width = 500;
            var height = 600;
            new daum.Postcode({
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
</body>
</html>