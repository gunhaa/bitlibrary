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
			<h1>결제내역</h1>
			<table id="list-table">
				<thead>
					<tr>
						<th>주문내역</th>
						<th>수량</th>
						<th>결제일</th>
						<th>금액</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) == 0}">
							<tr>
								<th style="height: 100px;" colspan="4">결제 내역이 없습니다</th>
							</tr>
						</c:when>
						
						<c:otherwise>
							<c:forEach var="l" items="${list}">
								<tr>
									<td>
										<c:choose>
											<c:when test="${l.TYPE_NO == 0}">
												식권
											</c:when>

											<c:otherwise>
												${l.FOOD_NAME}
											</c:otherwise>
										</c:choose>
									</td>
									<td>${l.FOOD_QTY}</td>
									<td>${l.PAY_DATE}</td>
									<td>${l.FOOD_PRICE}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<c:if test="${pagination.listCount > 0}">
				<div class="pagination-area">
					<ul class="pagination">
						<li><a href="myPayment?cp=1">&lt;&lt;</a></li>
						<li><a href="myPayment?cp=${pagination.prevPage}">&lt;</a></li>
						<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
							<c:choose>
								<c:when test="${i == pagination.currentPage}">
								<li><a class="current">${i}</a></li>
								</c:when>
								
								<c:otherwise>
								<li><a href="myPayment?cp=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li><a href="myPayment?cp=${pagination.nextPage}">&gt;</a></li>
						<li><a href="myPayment?cp=${pagination.maxPage}">&gt;&gt;</a></li>
					</ul>
				</div>
			</c:if>
		</section>
	</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	<script src="/resources/js/myPage/book.js"></script>
</body>
</html>