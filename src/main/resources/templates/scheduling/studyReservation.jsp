<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서관 예약 시스템</title>

    <link rel="stylesheet" href="/resources/css/scheduling/study-reservation.css">
    <link rel="stylesheet" href="/resources/css/scheduling/reservModal.css">
    <script src="https://kit.fontawesome.com/f4e088b372.js" crossorigin="anonymous"></script>

    <script>
        (() => {
            var link = document.createElement('link');
            link.rel = "shortcut icon";
            link.href = "/resources/images/logo.png";
            document.head.appendChild(link);
        })();
    </script>

</head>

<body>
    <header>
        <nav>
            <ul>
                <li>
                    <a href="/">
                        <img src="/resources/images/logo.png" alt="로고" id="homeLogo">
                        <span>도서관 예약 시스템</span>
                    </a>
                </li>

                <li><a href="/reservation/study"><span>열람실</span></a><a>&nbsp;|&nbsp;</a><a href="/reservation/seminar"><span>세미나실</span></a></li>
            </ul>

            <div class="header-top-menu">
                <c:choose>
                    <c:when test="${empty loginMember}">
                        <!-- 로그인 X -->
                        <a href="/member/login">로그인</a> | <a href="/member/signUp">회원가입</a>
                    </c:when>
            
                    <c:otherwise>
                        <!-- 로그인 O -->
                        <label for="headerMenuToggle"> ${loginMember.memberName}
                            <i class="fa-solid fa-caret-down"></i>
                        </label>
            
                        <input type="checkbox" id="headerMenuToggle">
            
                        <div class="header-menu">
                            <a href="/myPage/info">내 서재</a>
                            <a href="/member/logout">로그아웃</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>

    <section class="main-content-suround-section">
        <div id="reservationForm">
            <section class="content-suround-section">
                <div><span>열람실</span></div>
                <div>
                    <span>1. 예약 일자</span>
                    <input type="date" id="reservationDt" name="reservationDt" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                </div>
                <div>
                    <span>2. 예약 시간</span>
                    <input type="time" id="startTime" name="startTime" oninput="enforceTimeStep(this)">
                    ~ 
                    <input type="time" id="endTime" name="endTime" oninput="enforceTimeStep(this)" >
                    <button onclick="updateSeats()">조회</button>
                </div>
                <div>
                    <span>3. 예약 좌석 </span>
                    <span id="ment">예약할 좌석을 선택해 주세요.</span>
                    <span id="seatNo">seat(--)</span>
                    <span><button onclick="bookSeat()">예약</button></span>
                </div>
            </section>

            
            <section class="seat-status-section">

                <table>
                    <tr>
                        <th>열람실</th>
                        <th>전제좌석</th>
                        <th>사용좌석</th>
                        <th>잔여좌석</th>
                    </tr>
                    <tr>
                        <td>4층</td>
                        <td id="totalSeats">24</td>
                        <td id="usedSeats">--</td>
                        <td id="availableSeats">--</td>
                    </tr>
                </table>


                <table>
                    <tr>
                        <th id="selected-no">좌석번호</th>
                        <th>좌석번호</th>
                    </tr>
                    <tr>
                        <td id="selected-seat">사용중</td>
                        <td>공석</td>
                    </tr>
                </table>



            </section>

            <section class="seat-select-section" id="seatSection">
                <!-- 좌석 정보가 AJAX를 통해 동적으로 로드됩니다. -->
              


  
                <div class="empty-space"></div> <!-- 오른쪽 하단 빈 공간 -->
            </section>
        </div>

    </section>

    <footer>
        <article>
            <a href="#">개인정보처리방침</a>
            <span>|</span>
            <a href="#">영상정보처리기 운영·관리방침</a>
        </article>
        <div>
            <img src="/resources/images/logo.png">
            <div>
                <p>서울특별시 강남구 테헤란로 14길 6 (남도빌딩)</p>
                <p>전화번호 : 1544-9970</p>
                <p>이용시간 : 화~금 07:00 ~ 23:00 / 토,일 07:00~21:00 / 월요일, 공휴일 휴관</p>
            </div>
            <img src="/resources/images/wa_logo.png">
        </div>
    </footer>

     <!-- 알림창 모달 -->
     <div id="alert_modal" style="display: none;">
        <div class="popup_box">
            <div class="popup_header">
                <p>다음과 같이 좌석이 선택되었습니다.</p>
            </div>
            <div class="popup_content" id="alert_content">
                <p>해당 00좌석을 예약 하시겠습니까?</p>
            </div>
            <div class="popup_btn">
                <button id="confirm_btn">확인</button>
                <button type="button" id="cancel_btn">취소</button>
            </div>
        </div>
    </div>

       <!-- 예약정보 확인 모달 -->
    <div id="reservation_modal" style="display: none;">
        <div class="popup_box">
            <div class="popup_header">
                <p>다음과 같이 예약되었습니다.</p>
            </div>
            <div class="popup_content" id="reservation_content">
                <p>예약 좌석 : 00번</p>
                <p>예약 일자 : 2024-09-09</p>
                <p>예약 시간 : 09:00 ~ 12:00</p>
            </div>
            <div class="popup_btn">
                <button id="reserv_history">예약이력</a></button>
            </div>
        </div>
    </div>



    <script src="/resources/js/scheduling/study-reservation.js"></script>

</body>

</html>
