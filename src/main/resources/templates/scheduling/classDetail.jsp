<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="today" value="<%=new java.util.Date()%>"/>
<%-- 현재날짜 --%>
<c:set var="sysdate"><fmt:formatDate value="${today}" pattern="yyyy-MM-dd" /></c:set> 

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library - 교육문화 프로그램</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/common/sideMenu.css">
    <link rel="stylesheet" href="/resources/css/scheduling/class-detail.css">
    <link rel="stylesheet" href="/resources/css/scheduling/modal.css">

</head>

<body>

    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    
    <section class="main-content-suround-section">
        <article class="side-menu-article">
            <!-- 사이드 메뉴 -->
            <div class="category">참여 마당</div>
            <ul class="side-menu">
                <li class="side-current"> <a href="/scheduling/12" class="side-current-a">교육문화 프로그램</a></li>
                <li class="side1"><a href="/scheduling/13">열람실</a></li>
                <li class="side2"><a href="/scheduling/14">세미나실</a></li>
            </ul>

        </article>
     

        <!-- 공지사항 목록 -->
        <section class="content-suround-section">
            <div class="content-title">교육문화 프로그램</div>
            <div class="title-line"></div>

            <!-- 게시글 상세 화면 -->
            <section class="board-detail">
                <!-- 제목 -->
                <h1 class="board-title">${classBoard.boardTitle}</h1>

                <!-- 작성자 / 작성일 / 조회수 -->
                <div class="board-nav">
                    <span>신청기간 : ${classBoard.recruitmentStartDt} ~ ${classBoard.recruitmentEndDt}</span>|
                    <span>교육기간 : ${classBoard.startDt} ~ ${classBoard.endDt}</span>|
                    <span>최대인원 : ${classBoard.maxParticipant}</span>
                    
                </div>

                <!-- 이미지 보여지는 영역 -->
                <!-- 썸네일 X -->
                <div class="img-box">
                    <!-- 등록한 이미지가 있으면 보여줘야하는 영역 -->
                    <div class="boardImg">
                        <img src="${classBoard.imageList[0].imagePath}${classBoard.imageList[0].imageReName}">
                    </div>
                </div>

                <!-- 게시글 내용 -->
                <div class="board-content">
                    <pre>${classBoard.boardContent}</pre>
                </div>

                <!-- 버튼 영역(회원) -->
                <c:if test="${loginMember.authority == 1}">
                <div class="btn-area">
                    <c:if test="${classBoard.recruitmentStartDt <= sysdate && sysdate <= classBoard.recruitmentEndDt && classBoard.applicantCount < classBoard.maxParticipant}">
                    <!-- 신청(로그인 한 멤버번호== 작성한 멤버번호) -->
                    <button id="applyBtn" onclick="applyModal()">신청</button>
                    </c:if>

                    <button id="goToListBtn">목록으로</button>
                </div>
                </c:if>

                <c:if test="${empty loginMember.authority}">
                <div class="btn-area">
                    <button id="goToListBtn">목록으로</button>
                </div>
                </c:if>

                <!-- 버튼 영역(관리자) -->
                <c:if test="${loginMember.authority == 2}">
                <div class="btn-area">
                    <button id="updateBtn">수정</button>
                    <button id="deleteBtn" onclick="deleteModal()">삭제</button>

                    <button id="goToListBtn">목록으로</button>
                </div>
                </c:if>

            </section>

        </section>

    </section>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <!-- 알림창 모달-->
    <div id="popup_layer">
        <div class="popup_box">
            <div class="popup_header">
                <p>교육문화 프로그램</p>
            </div>

            <!--팝업 컨텐츠 영역-->
            <div class="popup_content"> 
                <c:if test="${loginMember.authority == 1}">
                <p> 해당 클래스를 신청하시겠습니까?</p>
                </c:if>
                <c:if test="${loginMember.authority == 2}">
                <p> 해당 게시글을 삭제하시겠습니까?</p>
                </c:if>
            </div>
            <!--팝업 버튼 영역-->
            <div class="popup_btn">
                <c:if test="${loginMember.authority == 1}">
                <button id="confirm_btn">확인</button>
                </c:if>
                <c:if test="${loginMember.authority == 2}">
                <button id="delete_confirm_btn">확인</button>
                </c:if>
                <button id="cancel_btn">취소</button>
            </div>
        </div>
    </div>

    
    <!-- 신청 실패 모달 -->
    <div id="applyModal" class="modal" style="display: none;">
        <div class="modal_box">
            <div class="modal_header">
                <p>교육문화 프로그램</p>
            </div>

            <div class="modal_content">
                <p>이미 신청한 클래스입니다.</p>
            </div>
            <div class="modal_btn">
                <button class="close_btn">확인</button>
            </div>
        </div>
    </div>


    <script>
        const boardNo = "${classBoard.boardNo}";
        const loginMemberNo ="${loginMember.memberNo}";
        const cateCode = "${cateCode}";
        const cp = "${cp}";
    </script>


    <script src="/resources/js/scheduling/class-detail.js"></script>
</body>

</html>