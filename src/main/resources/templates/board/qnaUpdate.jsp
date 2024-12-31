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
    <link rel="stylesheet" href="/resources/css/board/qnaUpdate-style.css">
</head>
<body>

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
            <form action="update" method="POST" class="board-write" id="boardWriteFrm">
                <!-- 제목 -->
                <h1 class="board-title">
                    <input type="text" name="boardTitle" placeholder="제목 입력" value="${board.boardTitle}">
                </h1>

                <!-- 작성자 이름 영역 -->
                <!-- db에서 memberName 불러올 것 -->
                <div class="member-name"><h3>${loginMember.memberName}</h3></div>

                <!-- 공개/비공개 -->
                <div class="board-type">
                    <span>공개 여부</span>

                    <input type="radio" name="boardSecretFlag" id="public" value="N">
                    <label for="public">공개</label>

                    <input type="radio" name="boardSecretFlag" id="private" value="Y">
                    <label for="private">비공개</label>
                </div>

                
                <!-- 내용 -->
                <div class="board-content">
                    <textarea name="boardContent">${board.boardContent}</textarea>
                </div>

                <!-- 버튼 영역 -->
                <div class="btn-area">
                    <button type="button" onclick="location.href='/board/${cateCode}/${boardNo}'">취소</button>
                    <button type="submit" id="writeBtn">등록</button>
                </div>

                <!-- 수정 성공 시 주소 유지 용도 -->
                <input type="hidden" name="cp" value="${param.cp}">

            </form>
        </section>


    </section>

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <script>

        const secret = "${board.boardSecretFlag}";

        const cateCode = "${cateCode}";

    </script>

    <%-- boardUpdate.js 연결 --%>
    <script src="/resources/js/board/boardUpdate.js"></script>

</body>
</html>