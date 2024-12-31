<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>Java Library - Food</title>
	<link rel="stylesheet" href="/resources/css/food/food.css">
	<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
	<link rel="stylesheet" href="/resources/css/main-style.css">
	<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
	<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	<script src="https://kit.fontawesome.com/cb5f1fee4d.js" crossorigin="anonymous"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/modal.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<section class="main">
		<jsp:include page="/WEB-INF/views/food/sideMenu.jsp"></jsp:include>
		
		<section class="main-content">
			<div class="content-title">구내식당</div>
			<div class="title-line"></div>

			<section class="content-detail-area">
				<article class="week-meal">
					<div class="week-meal-btn-area">
						<p class="meal-title">백반</p>
						<c:if test="${!empty loginMember }">
							<c:choose>
								<c:when test="${loginMember.authority == 1 }">
									<button id="pay-btn">식권 구매</button>
								</c:when>
								<c:otherwise>
									<div>
										<button id="menu-add-btn">메뉴 추가</button>
										<button id="menu-update-btn">메뉴 수정</button>
									</div>
								</c:otherwise>						
							</c:choose>
						</c:if>

					</div>
					<div class="food-table-area">
						<table>
							<thead>
								<tr>
									<td>월</td>
									<td>화</td>
									<td>수</td>
									<td>목</td>
									<td>금</td>
									<td>토</td>
									<td>일</td>
								</tr>
							</thead>
							<tbody>
								<!-- 고마워요 재능충~!~!~!~! -->
								<c:forEach var="food" items="${foodList}" varStatus="status">
									<c:if test="${food.typeNo == 0}">
										<c:if test ="${status.index % 6 == 0}">
											<tr>
										</c:if>
										
										<c:if test ="${status.index == 4}">
											<td rowspan="5" class="holiday">휴관일</td>
										</c:if>
											
										<td>${food.foodName}</td>
										
										<c:if test ="${status.index % 6 == 5}">
											</tr>
										</c:if>	
									</c:if>
								</c:forEach> 
							</tbody>	
						</table>
					</div>
				</article>
				<article class="snack">
					<p class="meal-title">분식</p>
					<div class="meal-detail">
						<div class="snack-menu">
							<c:forEach var="food" items="${foodList}" varStatus="status">
								<c:if test="${food.typeNo == 1}">
									<div class="snack-menu-detail">
										<c:if test="${loginMember.authority == 2 }">
											<button id="snack-update-btn" foodNo="${food.foodNo}"  foodName="${food.foodName}" foodImg="${food.foodImg}"><i class="fa-regular fa-pen-to-square"></i></button>
											<button id="menu-del-btn" foodNo="${food.foodNo}"><i class="fa-solid fa-xmark"></i></button>
										</c:if>
										<img src="${food.foodImg}">
										<p><strong>${food.foodName}</strong></p>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</article>
			</section>
		</section>

	</section>
		<script> 
			let addMenu = "${AddMenu}";
			let updateSnackMenu = "${UpdateSnackMenu}";
			let loginMemberNo = "${loginMember.memberNo}";
		</script>
	<script src="/resources/js/food/food.js" defer></script>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>