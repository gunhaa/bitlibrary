<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library - 교육문화 프로그램</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/common/sideMenu.css">
    <link rel="stylesheet" href="/resources/css/scheduling/class-write.css">
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

            <!-- 게시글 작성 화면 -->
            <section class="board-detail">
                <form action="update" method="POST" class="board-write" id="boardWriteFrm" enctype="multipart/form-data">

                    <h1 class="board-title">
                        <input type="text" name="boardTitle" placeholder="제목" value="${classSchedule.boardTitle}">
                    </h1>

                    <!-- 교육기간 / 신청기간 / 최대인원 -->
                    <div class="board-nav">
                        <span>신청기간 : <input type="date" name="recruitmentStartDt" value="${classSchedule.recruitmentStartDt}"> ~ <input type="date" name="recruitmentEndDt" value="${classSchedule.recruitmentEndDt}"></span>&nbsp;|&nbsp;
                        <span>교육기간 : <input type="date" name="startDt" value="${classSchedule.startDt}"> ~ <input type="date" name="endDt" value="${classSchedule.endDt}"></span>&nbsp;|&nbsp;
                        <span>최대인원 : <input type="number" style="width: 50px;" min="0" name="maxParticipant" value="${classSchedule.maxParticipant}"></span>
                    </div>

                    <c:forEach items="${classSchedule.imageList}" var="img">
                        <c:if test="${img.imageOrder == 0}">
                            <c:set var="img0" value="${img.imagePath}${img.imageReName}"/>
                        </c:if>
                    </c:forEach>


                    <!-- 이미지 등록 영역 -->
                    <div class="img-box">
                        <div class="boardImg">
                            <label for="img0">
                                <img src="${img0}" class="preview" id="preview">
                                <p id="img-ment">이미지 파일을 선택해 주세요.</p>
                            </label>
                            <input type="file" name="images" class="inputImage" id="img0" accept="image/*">
                            <span class="delete-image" id="delete-image">&times;</span>
                        </div>
                    </div>


                    <!--  내용 -->
                    <div class="board-content">
                        <textarea name="boardContent" placeholder="내용을 입력해 주세요.">${classSchedule.boardContent}</textarea>
                    </div>

                    <!-- 버튼 영역 -->
                    <div class="btn-area">
                        <button type="button" onclick="history.back()">취소</button>
                        <button type="submit" id="writeBtn">등록</button>
                    </div>

                    <!-- 기존 이미지가 있다가 삭제된 이미지의 순서를 기록 -->
                    <input type="hidden" name="deleteList" value="">

                     <!-- 수정 성공 시 주소(쿼리스트링) 유지 용도 -->
                    <input type="hidden" name="cp" value="${param.cp}">

                    <!-- 기존 이미지 파일명을 hidden 필드로 전송 -->
                    <input type="hidden" name="existingImage" value="${img0}">

                </form>

            </section>


        </section>

    </section>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />



    <script src="/resources/js/scheduling/class-write.js"></script>
</body>

</html>