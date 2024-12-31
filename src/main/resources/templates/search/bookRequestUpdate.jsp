<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
								<td><input type="text"  maxlength="300" value="${bookReq.requestTitle}"/></td>
							</tr>
							<tr>
								<th>신청일자</th>
								<td><input type="date" id="currentDate" value="${bookReq.requestDate}" readonly/></td>
							</tr>
							<tr>
								<th>신청자</th>
								<td>${bookReq.memberName}</td>
							</tr>
							<tr>
								<th>희망 도서명</th>
								<td><input type="text" value="${bookReq.bookTitle}"/></td>
							</tr>
							<tr>
								<th>저자</th>
								<td><input type="text" value="${bookReq.bookAuthor}"/></td>
							</tr>
							<tr>
								<th>출판사</th>
								<td><input type="text" value="${bookReq.bookPub}"/></td>
							</tr>
							<tr>
								<th>출판 연도</th>
								<td><input type="date" id="publishDate" value="${bookReq.bookPubDate}"/></td>
							</tr>
							<tr>
								<th class="opinion">추천의견</th>
								<td><textarea id="opinion" cols="20">${bookReq.requestOpinion}</textarea></td>
							</tr>

						</tbody>
					</table>
				</div>

				<div class="btn-area">
                    <button onclick="location.href='/book/2/1?cp=${param.cp}'">취소</button>
                    <button id="updateBtn">수정</button>
                </div>
			</section>
		</section>
		
	</section>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
		const loginMemberNo = "${loginMember.memberNo}";
		const requestNo = "${bookReq.requestNo}";
	</script>
	<script src="/resources/js/search/bookRequestUpdate.js"></script>
</body>
</html>