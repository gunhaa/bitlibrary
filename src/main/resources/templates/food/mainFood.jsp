<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.Calendar" %>

<%
    // 오늘 날짜에서 요일 가져오기 (0: 일요일, 1: 월요일, ..., 6: 토요일)
    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7;

    // dayOfWeek 값을 request에 저장
    request.setAttribute("dayOfWeek", dayOfWeek);
%>

<script src="https://kit.fontawesome.com/cb5f1fee4d.js" crossorigin="anonymous"></script>

<section class="main-food-area">
    <article>
    	<div class="today-menu-title">
	    	<i class="fa-solid fa-utensils"></i>오늘의 메뉴
    	</div>
    	<div id="goToFood"><a href="/food"><i class="fa-solid fa-plus"></i></a></div>
    </article>
    <article class="main-food-info-area">
        <div class="main-today-menu">
        	<c:if test="${dayOfWeek == 5 }">
        		<div class="main-food-closed-day">휴관일</div>
        	</c:if>
        	
       	  	<!-- 토요일일 경우 특정 음식 번호(5, 11, 17, 23, 29) 출력 -->
            <c:if test="${dayOfWeek == 6}">
                <c:forEach var="food" items="${foodList}" varStatus="status">
                    <c:if test="${food.foodNo == 5 || food.foodNo == 11 || food.foodNo == 17 || food.foodNo == 23 || food.foodNo == 29}">
                        <div>${food.foodName}</div>
                    </c:if>
                </c:forEach>
            </c:if>

            <!-- 다른 요일 (일, 월, 화, 수, 목)일 경우 -->
            <c:if test="${dayOfWeek != 5 && dayOfWeek != 6}">
                <c:forEach var="food" items="${foodList}" varStatus="status">
                    <c:if test="${food.typeNo == 0}">
                        <!-- 요일에 맞는 음식을 나머지 연산으로 매칭하여 출력 -->
                        <c:if test="${food.foodNo % 6 == dayOfWeek}">
                            <div>${food.foodName}</div>
                        </c:if>
                    </c:if>
                </c:forEach>
            </c:if>
        </div>
        <div class="main-time-info">
            <div class="main-restaurant-time">
                <p><strong>식당+매점 운영시간</strong></p>
                <p>09:00 ~ 20:00</p>
            </div>
            <div class="main-cafe-time">
                <p><strong>카페 운영시간</strong></p>
                <p>09:00 ~ 20:00</p>
            </div>
        </div>
    </article>
    
</section>

