<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 일정 편집</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/edit_popup.css">
<link rel="stylesheet" href="/resources/css/intro/popup.css">
<link rel="stylesheet" href="/resources/css/intro/del-popup.css">
<link rel="stylesheet" href="/resources/css/intro/library_calendar_edit.css">

<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
			<div class="content-title">도서관 달력</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div class="calendar-category-suround">
                    <span class="calendar-category"> <span class="category-color hue"></span> 휴관일</span>
                    <span class="calendar-category"> <span class="category-color eventDay"></span> 행사</span>
                </div>

                <div class="title-line"></div>

                <div class="calendar-surround">
                    <div class="surround-calendar-area">
                        <div class="calendar-btn-area">
                            <div id="prev-btn">&lt;</div>
                            <div id="today-btn">오늘</div>
                            <div id="next-btn">&gt;</div>
                        </div>
                        <div id='calendar'></div>
                    </div>

                    <div class="todayList-surround">
                        <div id="date-div"></div>
                        <div class="todo-surround">
                            <table>
                                <thead>
                                    <tr>
                                        <th>일정</th>
                                        <th>시작일</th>
                                        <th>종료일</th>
                                        <th>종류</th>
                                        <th>비고</th>
                                    </tr>
                                    
                                </thead>

                                <tbody>
                                </tbody>

                                <tfoot>
                                    <tr>
                                        <th id="add-todo" colspan="5">+ 일정 추가</th>
                                    </tr>
                                </tfoot>

                            </table>
                        </div>

                    </div>
                </div>
            </section>
      </section>
     
		
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	<!-- 모달 영역 -->
	<!-- 편집 모달 -->
        <div id="edit-popup_layer">
          <div class="edit-popup_box">
                <div class="edit-popup_header">
                    <p>일정 편집</p>
                <span id="edit-popup-close">&times;</span>
                </div>
                
              <!--팝업 컨텐츠 영역-->
              <div class="edit-popup_content">
                <div>
                    <div class="scheduleName--surround">
                        <span>일정 이름</span>
                        <input type="text" name="scheduleName"/>
                    </div>
                    <div class="startDate-surround">
                        <span>시작일</span>
                        <input type="date" name="startDate" value="">
                    </div>
                    <div class="endDate-surround">
                        <span>종료일</span>
                        <input type="date" name="endDate" value="">
                    </div>
                    <div class="calCategory-surround">
                        <span>종류</span>
                        <select name="calendarCategory" id="calendarCategory">
                            <option value="2">휴관일</option>
                            <option value="3">행사</option>
                        </select>
                    </div>
                </div>
              </div>
              <!--팝업 버튼 영역-->
              <div class="edit-popup_btn">
                  <button id="edit-confirm_btn" onClick="insertSchedule()">등록</button>
              </div>

          </div>
        </div>
        </div>

        <!-- 알림창 모달-->
	<div id="del-popup_layer">
		<div class="del-popup_box">
			  <div class="del-popup_header">
				  <p>일정 삭제</p>
			  </div>
			  
			<!--팝업 컨텐츠 영역-->
			<div class="del-popup_content">
				<p> 정말 삭제 하시겠습니까?
				</p>
			</div>
			<!--팝업 버튼 영역-->
			<div class="del-popup_btn">
				<button id="del-confirm_btn">확인</button>
				<button id="del-cancel_btn">취소</button>
			</div>
		</div>
	  </div>

    <!--알림창 모달-->
	<div id="popup_layer">
		<div class="popup_box">
            <div class="popup_header">
                <p>도서관 일정 편집</p>
            </div>
			  
			<!--팝업 컨텐츠 영역-->
			<div class="popup_content">
				<p></p>
			</div>

			<!--팝업 버튼 영역-->
			<div class="popup_btn">
				<button id="confirm_btn">확인</button>
			</div>

        </div>
	</div>
        
    <script src="/resources/js/intro/lib_cal_edit.js"></script>
	
</body>
</html>