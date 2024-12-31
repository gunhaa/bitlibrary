<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Java Library - Cafe</title>
	
	<link rel="stylesheet" href="/resources/css/food/cafe.css">
	<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
	<link rel="stylesheet" href="/resources/css/main-style.css">
	<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
	<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	<script src="https://kit.fontawesome.com/cb5f1fee4d.js" crossorigin="anonymous"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/modal.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<section class="main">
		<jsp:include page="/WEB-INF/views/food/sideMenu.jsp"></jsp:include>
		
		<section class="main-content">
			<div class="content-title">카페</div>
			<div class="title-line"></div>

			<section class="content-detail-area">
				<div class="cafe-btn-area">
					<c:if test="${!empty loginMember }">
						<c:choose>
							<c:when test="${loginMember.authority == 1 }">
								<button id="pay-btn">음료 구매</button>
							</c:when>
							<c:otherwise>
								<div>
									<button id="menu-add-btn">메뉴 추가</button>
								</div>
							</c:otherwise>						
						</c:choose>
					</c:if>
					
				</div>

				<article class="cafe-menu">
				
					<c:forEach var="food" items="${foodList}" varStatus="status">
						<c:if test="${food.typeNo == 2}">
							<div class="cafe-menu-area">
								<c:if test="${loginMember.authority == 2 }">
									<button id="cafe-update-btn" foodNo="${food.foodNo}" foodName="${food.foodName}" foodPrice="${food.foodPrice}" foodImg="${food.foodImg}" foodDes="${food.foodDes}"><i class="fa-regular fa-pen-to-square"></i></button>
									<button id="menu-del-btn" foodNo="${food.foodNo}"><i class="fa-solid fa-xmark"></i></button>
								</c:if>
								<img src="${food.foodImg}">
								<p>${food.foodName}</p>
								<p>${food.foodPrice}원</p>
							</div>
						</c:if>
					</c:forEach>
				</article>
			</section>
		</section>
	</section>
	<script> 
		let addMenu = "${AddMenu}";
		let updateMenu = "${UpdateMenu}";
		let loginMemberNo = "${loginMember.memberNo}";
	</script>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script src="/resources/js/food/cafe.js"></script>
</body>
</html>