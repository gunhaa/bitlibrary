<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pagination" value="${map.pagination}"/>
<c:set var="list" value="${map.list}"/>

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
			<p>신청내역</p>
			<table id="list-table">
				<thead>
					<tr>
						<th style="width: 100px;">신청번호</th>
						<th>신청 도서명</th>
						<th>저자</th>
						<th>출판사</th>
						<th style="width: 100px;">신청상태</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) == 0}">
							<tr>
								<th style="height: 100px;" colspan="5">신청 내역이 없습니다</th>
							</tr>
						</c:when>
						
						<c:otherwise>
							<c:forEach var="b" items="${list}">
								<tr>
									<td>${b.REQ_BOOK_NO}</td>
									<td>${b.BOOK_TITLE}</td>
									<td>${b.BOOK_AUTHOR}</td>
									<td>${b.BOOK_PUB}</td>
									<td>
										<c:if test="${b.REQ_APPROVE == 'W'}">대기</c:if>
										<c:if test="${b.REQ_APPROVE == 'Y'}">승인</c:if>
										<c:if test="${b.REQ_APPROVE == 'N'}">거절</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<c:if test="${empty param.m}">
				<c:set var="m" value="1"/>
			</c:if>
			
			<c:if test="${!empty param.m}">
				<c:set var="m" value="${param.m}"/>
			</c:if>
			
			<c:if test="${pagination.listCount > 0}">
				<div class="pagination-area">
					<ul class="pagination">
						<li><a href="book?m=${m}&cp=1">&lt;&lt;</a></li>
						<li><a href="book?m=${m}&cp=${pagination.prevPage}">&lt;</a></li>
						<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
							<c:choose>
								<c:when test="${i == pagination.currentPage}">
								<li><a class="current">${i}</a></li>
								</c:when>
								
								<c:otherwise>
								<li><a href="book?m=${m}&cp=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li><a href="book?m=${m}&cp=${pagination.nextPage}">&gt;</a></li>
						<li><a href="book?m=${m}&cp=${pagination.maxPage}">&gt;&gt;</a></li>
					</ul>
				</div>
			</c:if>
		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script src="/resources/js/my/library.js"></script>
</body>
</html>