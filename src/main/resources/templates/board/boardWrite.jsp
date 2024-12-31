<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<c:set var="pagination" value="${map.pagination}" />
<c:set var="boardList" value="${map.boardList}" />

<c:forEach items="${category}" var="catelist">
    <c:if test="${catelist.CAT_CODE == cateCode}">
        <c:set var="cateName" value="${catelist.CAT_NAME}"/>
    </c:if>
</c:forEach>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library - ${cateName}</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/board/boardWrite-style.css">
</head>
<body>

    <!-- 공지사항 / 책 후기 나눠요 공통 화면 -->

    <!-- header -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <section class="main-content-suround-section">
        <article class="side-menu-article">
            <!-- 사이드 메뉴 -->
            <div class="category">이용자 마당</div>
            <ul class="side-menu">
                <li><a href="/board/15">공지사항</a></li>
                <li><a href="/board/16">문의사항</a></li>
                <li><a href="/board/17">자주 묻는 질문</a></li>
                <li class="last-menu"><a href="/board/18">책 후기 나눠요</a></li>
            </ul>

        </article>

        <!-- 공지사항 목록 -->
        <section class="content-suround-section">
            <div class="content-title">${cateName}</div>
            <div class="title-line"></div>

            <!-- 게시글 작성 화면 -->
            <!-- notice / bookreview 공통 -->
            <form action="/board2/${cateCode}/insert" method="POST" class="board-write" id="boardWriteFrm" enctype="multipart/form-data">
                <!-- 제목 -->
                <h1 class="board-title">
                    <input type="text" name="boardTitle" placeholder="제목 입력">
                </h1>

                <!-- 이미지 등록 영역 -->
                <h5>이미지 등록</h5>
                <div class="img-box">
                    <div class="boardImg">
                        <label for="img0">
                            <img src="" class="preview">
                        </label>
                        <input type="file" name="images" class="inputImage" id="img0" accept="image/*">
                        <span class="delete-image">&times;</span>
                    </div>
    
                    <div class="boardImg">
                        <label for="img1">
                            <img src="" class="preview">
                        </label>
                        <input type="file" name="images" class="inputImage" id="img1" accept="image/*">
                        <span class="delete-image">&times;</span>
                    </div>
                </div>

                
                <!-- 내용 -->
                <div class="board-content">
                    <textarea name="boardContent"></textarea>
                </div>

                <!-- 버튼 영역 -->
                <div class="btn-area">
                    <button type="button" onclick="location.href='/board/${cateCode}'">취소</button>
                    <button type="submit" id="writeBtn">등록</button>
                </div>

            </form>
        </section>


    </section>

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <%-- boardWrite.js 연결 --%>
    <script src="/resources/js/board/boardWrite.js"></script>

    <script>
        const cateCode = "${cateCode}";

        const li = document.querySelectorAll(".side-menu > li");

        switch(cateCode){
            case "15" : li[0].classList.add("side-current"); break;
            case "16" : li[1].classList.add("side-current"); break;
            case "17" : li[2].classList.add("side-current"); break;
            case "18" : li[3].classList.add("side-current"); break;
        } 

    </script>

</body>
</html>