<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pagination" value="${map.pagination}"/>
<c:set var="commentList" value="${map.commentList}"/>

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
			<h1>댓글 관리</h1>
			<div>
				<label for="all">
					<input type="checkbox" id="all"> 전체선택
				</label>
			</div>
			
			<form action="3" method="POST" name="adminFrm">
				<table id="list-table" style="table-layout:fixed;">
					<thead>
						<tr>
							<th width="130px">삭제여부 변경</th>
							<th>내용</th>
							<th width="120px">작성자</th>
							<th width="120px">작성일</th>
							<th width="100px">삭제여부</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="comment" items="${commentList}">
							<tr>
								<td>
									<input type="checkbox">
									<input type="hidden" value="${comment.commentNo}">
								</td>
								<td>${comment.commentContent}</td>
								<td>${comment.memberName}</td>
								<td>${comment.commentCreateDate}</td>
								<td>${comment.commentDeleteFlag}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			
				<c:if test="${pagination.listCount > 0}">
					<div class="pagination-area">
						<ul class="pagination">
							<li><a href="3?cp=1">&lt;&lt;</a></li>
							<li><a href="3?cp=${pagination.prevPage}">&lt;</a></li>
							<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
								<c:choose>
									<c:when test="${i == pagination.currentPage}">
									<li><a class="current">${i}</a></li>
									</c:when>
									
									<c:otherwise>
									<li><a href="3?cp=${i}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<li><a href="3?cp=${pagination.nextPage}">&gt;</a></li>
							<li><a href="3?cp=${pagination.maxPage}">&gt;&gt;</a></li>
						</ul>
					</div>
				</c:if>
				
				<input type="hidden" name="updateList">
				<button class="admin-submit">확인</button>
			</form>
		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/resources/js/admin/admin.js"></script>
</body>
</html>