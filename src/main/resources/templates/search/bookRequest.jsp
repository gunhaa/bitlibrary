<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Java Library</title>
<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/search/bookRequest-style.css">
</head>
<body>
	
		<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
		<section class="main">
			<jsp:include page="/WEB-INF/views/search/sideMenu.jsp"></jsp:include>

			<section class="main-content">
				<div class="main-title">희망 도서 신청</div>
				<hr class="titleLine"/>
				<section class="body-content">
					<div class="body-title">신청 안내</div>
					<!-- 신청 안내 박스 -->
					<div class="apply-box">
						<div class="apply-box-index">※희망 도서 신청시 다양한 주제 선정을 위해서 신청하신 도서가 선정되지 않을 수 있으니 
							<br>양해 부탁드립니다.</div>
					</div>
					
					<!-- 상세 내용 -->
					<div class="apply-book-detail">
						<%-- <div class="line"></div> --%>
						<div class="apply-book-title">희망 자료 신청</div>
						<div class="apply-book-details">
							<ul>
								<li>신청기간: 상시 / 예산 소진 시 </li>
								<li>신청자격: JAVA LIBRARY 회원</li>
								<li>신청권수: 회원 1인 당 3권</li>
								<li>신청방법: 도서관 홈페이지 (로그인 → 자료 검색 → 희망 자료 신청)</li>
							</ul>
						</div>
						
						<div class="apply-book-title">희망 자료 신청 제외</div>
						<div class="apply-book-details">
							<ul>
								<li>품절 / 절판 도서</li>
								<li>전문 도서 및 고가 도서(50,000원 초과)</li>
								<li>과도한 개인 신앙 도서, 과도한 정치 편향 도서</li>
								<li>그 외에 내부 심의를 통해 논란 발생 우려가 있을 경우 제외</li>
							</ul>
						</div>
						
						<div class="apply-book-title">희망 자료 신청 처리</div>
						<div class="apply-book-details">
							<ul>
								<li>소요기간: 평균 20일 내외 </li>
								<li>처리절차: 신청 → 검토 → 주문 → 납품 → 등록 작업 → 비치</li>
								<li>처리 상황 안내</li>
								<li>"신청중", "처리중", "소장중" 으로 홈페이지상에 안내되며, 해당 도서 자료실 비치 시 대출 안내 문자메세지를 발송합니다.</li>
							</ul>
						</div>
						
						<!-- <hr class="last-line"> -->
						<div class="apply-area">
							<div class="apply-text">희망 자료 신청 또는 조회를 하려면 버튼을 누르세요 </div>
							<div class="apply-btn-area">
								<button id="apply-btn" onclick="location.href='2/1'">조회</button>
								<button id="request-btn">신청</button>
							</div>
						</div>
						
					</div>
				</section>
			</section>
		</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
		const memberNo = "${loginMember.memberNo}";
		document.getElementById("request-btn").addEventListener("click", ()=>{
			if(memberNo == ""){
				alert("로그인 후 이용가능합니다.")
			}else{
				location.href='2/2';
			}
		})
	</script>
</body>
</html>