<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pagination" value="${map.pagination}"/>
<c:set var="reqList" value="${map.reqList}"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Java Library</title>
	<link rel="stylesheet" href="/resources/css/search/bookRequest-style.css">
	<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
	<link rel="stylesheet" href="/resources/css/main-style.css">
	<link rel="stylesheet" href="/resources/css/board/reviewList-style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<section class="main">
		<jsp:include page="/WEB-INF/views/search/sideMenu.jsp"></jsp:include>

		<section class="main-content">
			<div class="main-title">신청 내역</div>
			<hr class="titleLine" />
			<section class="body-content">
				<section class="board-list">
					<div class="list-wrapper">
						<table class="list-table">
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>작성자</th>
									<th>작성일</th>
									<th>승인여부</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${empty reqList}">
										<tr>
											<th colspan="5">게시글이 존재하지 않습니다.</th>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="request" items="${reqList}">
											<tr>
												<td>${request.requestNo}</td>
												<td><a href="/book/2/1/${request.requestNo}?cp=${pagination.currentPage}">${request.requestTitle}</a></td>
												<td>${fn:substring(request.memberName, 0,1)}*<c:if test="${fn:length(request.memberName) > 2}">${fn:substring(request.memberName, 2, fn:length(request.memberName))}</c:if></td>
												<td>${request.requestDate}</td>
												<td>
													<c:if test="${request.requestApprove == 'W'}">대기중</c:if>
													<c:if test="${request.requestApprove == 'Y'}">승인</c:if>
													<c:if test="${request.requestApprove == 'N'}">거절</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>

					<c:if test="${pagination.listCount > 0}">
						<div class="pagination-area">
							<ul class="pagination">
								<li><a href="/book/2/1?cp=1">&lt;&lt;</a></li>
								<li><a href="/book/2/1?cp=${pagination.prevPage}">&lt;</a></li>
								<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
									<c:choose>
										<c:when test="${i == pagination.currentPage}">
										<li><a class="current">${i}</a></li>
										</c:when>
										
										<c:otherwise>
										<li><a href="/book/2/1?cp=${i}">${i}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<li><a href="/book/2/1?cp=${pagination.nextPage}">&gt;</a></li>
								<li><a href="/book/2/1?cp=${pagination.maxPage}">&gt;&gt;</a></li>
							</ul>
						</div>
					</c:if>

					<div class="btn-area">
						<c:if test="${!empty loginMember}">
							<button id="writeBtn" onclick="location.href='/book/2/2/'">신청</button>
						</c:if>
					</div>
				</section>
			</section>
		</section>
	</section>

	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>