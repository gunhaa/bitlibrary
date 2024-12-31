<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library - 세미나실</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/common/sideMenu.css">
    <link rel="stylesheet" href="/resources/css/scheduling/study.css">
    <script src="https://kit.fontawesome.com/f4e088b372.js" crossorigin="anonymous"></script>

</head>

<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <section class="main-content-suround-section">
        <article class="side-menu-article">
            <!-- 사이드 메뉴 -->
            <div class="category">참여 마당</div>
            <ul class="side-menu">
                <li class="side"> <a href="/scheduling/12" >교육문화 프로그램</a></li>
                <li class="side1"><a href="/scheduling/13">열람실</a></li>
                <li class="side-current"><a href="/scheduling/14" class="side-current-a">세미나실</a></li>
            </ul>

        </article>

        <!-- 공지사항 목록 -->
        <section class="content-suround-section">
            <div class="content-title">세미나실 이용안내</div>
            <div class="title-line"></div>


            <div class="c1">로그인</div>
            <ul class="u1">
                <li>로그인 페이지에서 아이디, 비밀번호 입력 후 확인 버튼을 눌러서 로그인 합니다.</li>
                <li>회원가입을 하지 않은 경우 먼저 홈페이지에서 회원가입을 하시기 바랍니다.</li>
            </ul>
            <br>

            <div class="c1">세미나실 예약</div>
            <ul class="u1">
                <li>원하는 날짜를 선택한 후 시간을 조회를 합니다.</li>
                <li>조회된 시간테이블에서 예약 가능한 시간을 선택합니다.</li>
            </ul>
            <div class="reservation-container">
                세미나실 좌석현황을 확인하시려면 버튼을 누르세요.
                <button class="seminar" id="seminar">세미나실 예약</button>
            </div>
            <br>

            <div class="c1">불이행 기준</div>
            <ul class="u1">
                <li>참석인증 지연 : 30분 이내 참석인증하지 않을 경우 예약취소 및 불이행
                    최소인원 미달 : 불이행 1회</li>
                <li>불이행 3회 누적 시 이용제한 3일(이용제한 시 당일 이용가능, 익일부터 이용제한)</li>
                <li>참석인증은 디지털도서관 내 안내데스크에서 할 수 있습니다.</li>
            </ul>




        </section>


    </section>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <script>
        const loginMemberNo ="${loginMember.memberNo}";
    </script>

    <script src="/resources/js/scheduling/seminar.js"></script>

</body>

</html>