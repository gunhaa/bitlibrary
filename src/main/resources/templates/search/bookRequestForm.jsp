<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Java Library</title>
<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/common/modal.css">
<link rel="stylesheet" href="/resources/css/search/bookRequest-style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<section class="main">
		<jsp:include page="/WEB-INF/views/search/sideMenu.jsp"></jsp:include>

		<section class="main-content">
			<div class="main-title">신청 양식</div>
			<hr class="titleLine" />
			<section class="body-content">
				<div class="request-form">
					<table>
						<tbody>
							<tr class="request-tr">
								<th>제목</th>
								<td><input type="text"  maxlength="300" placeholder="제목 입력"/></td>
							</tr>
							<tr>
								<th>신청일자</th>
								<td><input type="date" id="currentDate" readonly /></td>
							</tr>
							<tr>
								<th>신청자</th>
								<td>${loginMember.memberName}</td>
							</tr>
							<tr>
								<th>희망 도서명</th>
								<td><input type="text" placeholder="도서명 입력" /></td>
							</tr>
							<tr>
								<th>저자</th>
								<td><input type="text" placeholder="저자 입력" /></td>
							</tr>
							<tr>
								<th>출판사</th>
								<td><input type="text" placeholder="출판사 입력"/></td>
							</tr>
							<tr>
								<th>출판 연도</th>
								<td><input type="date" id="publishDate"  placeholder="출판 연도 입력" /></td>
							</tr>
							<tr>
								<th class="opinion">추천의견</th>
								<td><textarea id="opinion" cols="20"  placeholder="내용 입력"></textarea></td>
							</tr>

						</tbody>
					</table>
				</div>
				<div class="btn-area">
					<button id="request-btn">신청하기</button>
				</div>
			</section>
		</section>
		
	</section>

	<jsp:include page="/WEB-INF/views/search/requestModal.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
		const loginMemberNo = "${loginMember.memberNo}"
	</script>
	<script src="/resources/js/search/bookRequest.js"></script>
</body>
</html>