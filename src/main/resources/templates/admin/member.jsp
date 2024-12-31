<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pagination" value="${map.pagination}"/>
<c:set var="memberList" value="${map.memberList}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Java Library</title>
	<link rel="stylesheet" href="/resources/css/main-style.css">
	<link rel="stylesheet" href="/resources/css/admin/list-style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<section class="container">
		<jsp:include page="/WEB-INF/views/admin/sideMenu.jsp"/>

		<section class="list-area">
			<h1>회원 관리</h1>
			<div>
				<label for="all">
					<input type="checkbox" id="all"> 전체선택
				</label>
			</div>
			
			<form action="1" method="POST" name="adminFrm">
				<table id="list-table">
					<thead>
						<tr>
							<th>탈퇴여부 변경</th>
							<th>이름</th>
							<th>생년월일</th>
							<th>가입일</th>
							<th>탈퇴여부</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="member" items="${memberList}">
							<tr>
								<td>
									<input type="checkbox">
									<input type="hidden" value="${member.memberNo}">
								</td>
								<td>${member.memberName}</td>
								<td>${member.memberDateOfBirth}</td>
								<td>${member.enrollDate}</td>
								<td>${member.memberDeleteFlag}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			
				<div class="pagination-area">
					<ul class="pagination">
						<li><a href="1?cp=1">&lt;&lt;</a></li>
						<li><a href="1?cp=${pagination.prevPage}">&lt;</a></li>
						<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
							<c:choose>
								<c:when test="${i == pagination.currentPage}">
								<li><a class="current">${i}</a></li>
								</c:when>
								
								<c:otherwise>
								<li><a href="1?cp=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li><a href="1?cp=${pagination.nextPage}">&gt;</a></li>
						<li><a href="1?cp=${pagination.maxPage}">&gt;&gt;</a></li>
					</ul>
				</div>
				
				<input type="hidden" name="updateList">
				<button class="admin-submit">확인</button>
			</form>
		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/resources/js/admin/admin.js"></script>
</body>
</html>