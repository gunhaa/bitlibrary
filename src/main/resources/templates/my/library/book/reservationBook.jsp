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
    <link rel="stylesheet" href="/resources/css/my/list-style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="container">
		<jsp:include page="/WEB-INF/views/my/sideMenu.jsp"/>
		
		<section class="list-area">
			<h1>도서현황</h1>
			<div class="nav-area">
				<a href="book?m=1">대출중인 도서</a>
				<a href="book?m=2">이전 대출내역</a>
				<a href="book?m=3">예약내역</a>
				<a href="book?m=4">신청내역</a>
			</div>
			<p>예약내역</p>
			<table id="list-table">
				<thead>
					<tr>
						<th style="width: 100px;">예약번호</th>
						<th>도서명</th>
						<th style="width: 120px;">신청일자</th>
						<th style="width: 120px;">예약만료일</th>
						<th style="width: 100px;">예약취소</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(bList) == 0}">
							<tr>
								<th style="height: 100px;" colspan="5">예약 내역이 없습니다</th>
							</tr>
						</c:when>
						
						<c:otherwise>
							<c:forEach var="l" items="${bList}">
								<tr>
									<td>${l.RESERVATION_NO}</td>
									<td>${l.BOOK_TITLE}</td>
									<td>${l.RESERVATION_DT}</td>
									<td>${l.RESERVATION_DT_E}</td>
									<td><button onclick="bookcancel(${l.RESERVATION_NO})">취소</button></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script>
		const loginMemberNo = "${loginMember.memberNo}";
	</script>
	<script src="/resources/js/my/library.js"></script>
</body>
</html>