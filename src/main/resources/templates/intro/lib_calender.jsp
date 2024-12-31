<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 도서관 달력</title>
	
	 <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/common/sideMenu.css">
    <link rel="stylesheet" href="/resources/css/intro/library_calendar.css">
    
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
            <section class="content-detail-section">
                <div class="content-title">도서관 달력</div>
            	<div class="title-line"></div>

	            <section class="content-detail-section">
	                <div class="cate-button-surround">
	                    <div class="calendar-category-suround">
	                        <span class="calendar-category" onclick="typeCalendar(1)"> <span class="category-color gong"></span> 공휴일</span>
	                        <span class="calendar-category" onclick="typeCalendar(2)"> <span class="category-color hue"></span> 휴관일</span>
	                        <span class="calendar-category" onclick="typeCalendar(4)"> <span class="category-color classDay"></span> 클래스</span>
	                        <span class="calendar-category" onclick="typeCalendar(3)"> <span class="category-color eventDay"></span> 행사</span>
	                    </div>

						<c:if test="${loginMember.authority == 2}">
	                    	<button id="calendar-edit-btn">일정편집</button>
						</c:if>
	                </div>
					<div>
						※ 클래스와 행사는 휴관일, 공휴일에 하지 않습니다. ※
					</div>
	                <div class="title-line"></div>
	
	                <div class="surround-calendar-area">
	                    <div class="calendar-btn-area">
	                        <div id="prev-btn">&lt;</div>
	                        <div id="today-btn">오늘</div>
	                        <div id="next-btn">&gt;</div>
	                    </div>
	                    <div id='calendar'></div>
	                </div>
	
	                <div class="title-line"></div>
	
	                <div id="date-div"></div>
	                <div class="title-line"></div>
						<div id="selectCalArea">
						</div>
	                <div class="title-line"></div>
	                
	            </section>
            </section>
		</section>
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	<script src="/resources/js/intro/lib_calender.js"></script>
     

</body>
</html>