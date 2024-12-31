<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서관 예약 시스템</title>
    <link rel="stylesheet" href="/resources/css/scheduling/seminar-reservation.css">
    <link rel="stylesheet" href="/resources/css/scheduling/reservModal.css">
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>

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
        <section class="content-suround-section">
            <div><span>세미나실</span></div>

            <form action="">
                <div>
                    <span>예약일 :</span><span id="date-div"></span>
                </div>

                <div class="surround-calendar-area">
                    <div class="calendar-btn-area">
                        <div id="prev-btn">&lt;</div>
                        <div id="today-btn">오늘</div>
                        <div id="next-btn">&gt;</div>
                    </div>
                    <div id='calendar'></div>
                </div>

                <div class="reservation-time">
                    <span>예약시간</span>
                </div>
                <div id="time-slot-table" class="time-slot-table">
                    <!-- 예약 가능한 시간표가 여기에 표시됩니다 -->
                </div>

                <div class="button-area">
                    <button type="button" id="reserve-btn">예약</button>
                </div>
            </form>
        </section>
    </section>

    <footer>
        <article>
            <a href="#">개인정보처리방침</a>
            <span>|</span>
            <a href="#"> 영상정보처리기 운영·관리방침 </a>
        </article>
        <div>
            <img src="/resources/images/logo.png">
            <div>
                <p>서울특별시 강남구 테헤란로 14길 6 (남도빌딩) </p>
                <p> 전화번호 : 1544-9970 </p>
                <p> 이용시간 : 화~금 07:00 ~ 23:00 / 토,일 07:00~21:00 / 월요일, 공휴일 휴관</p>
            </div>
            <img src="/resources/images/wa_logo.png">
        </div>
    </footer>

         <!-- 알림창 모달 -->
         <div id="alert_modal" style="display: none;">
            <div class="popup_box">
                <div class="popup_header">
                    <p>다음과 같이 세미나실이 선택되었습니다.</p>
                </div>
                <div class="popup_content" id="alert_content">
                    <p>해당 일에 세미나실을 예약 하시겠습니까?</p>
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
                    <p>세미나실</p>
                    <p>예약 일자 : 2024-09-09</p>
                    <p>예약 시간 : 09:00 ~ 12:00</p>
                    <p>* 세미나실의 위치는 시설안내를 통해 확인하세요</p>
                </div>
                <div class="popup_btn">
                    <button id="reserv_history"><a>예약이력</a></button>
                </div>
            </div>
        </div>
    
        <script>
            const loginMemberNo ="${loginMember.memberNo}";
        </script>

    <script src="/resources/js/scheduling/seminar-reservation.js"></script>
</body>

</html>