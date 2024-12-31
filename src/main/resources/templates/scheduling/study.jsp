<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library - 열람실</title>

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
                <li class="side1"> <a href="/scheduling/12" >교육문화 프로그램</a></li>
                <li class="side-current"><a href="/scheduling/13" class="side-current-a">열람실</a></li>
                <li class="side2"><a href="/scheduling/14">세미나실</a></li>
            </ul>

        </article>

        <!-- 공지사항 목록 -->
        <section class="content-suround-section">
            <div class="content-title">열람실 이용안내</div>
            <div class="title-line"></div>

            <div class="c1">로그인</div>
            <ul class="u1">
                <li>무인좌석발급기에서 아이디, 비밀번호 입력 후 확인 버튼을 눌러서 로그인 합니다.</li>
                <li>회원가입을 하지 않은 경우 먼저 홈페이지에서 회원가입을 하시기 바랍니다.</li>
            </ul>
            <br>

            <div class="c1">좌석 선택</div>
            <ul class="u1">
                <li>원하는 날짜와 시간을 선택한 후 조회를 합니다.</li>
                <li>선택 가능한 좌석 중에서 원하는 좌석을 선택합니다.</li>
            </ul>
            <div class="reservation-container">
                열람실 좌석현황을 확인하시려면 버튼을 누르세요.
                <button class="seat" id="seat">좌석현황 확인</button>
            </div>
            <br>

            <div class="c1">이용예절</div>
            <ul class="u1">
                <li>실내 정숙 : 휴대폰은 진동 모드로 설정하고, 대화는 최대한 조용하게 합니다.</li>
                <li>책상 정리 : 사용한 좌석은 깨끗하게 정리합니다.</li>
                <li>음식물 반입 금지 : 해당 열람실에서는 음식물 반입을 금지하고 있습니다.</li>
            </ul>




        </section>


    </section>


    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <script>
        const loginMemberNo ="${loginMember.memberNo}";
    </script>

    <script src="/resources/js/scheduling/study.js"></script>

</body>

</html>