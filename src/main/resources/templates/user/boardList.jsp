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
    <link rel="stylesheet" href="/resources/css/board/boardList-style.css">
    <script src="https://kit.fontawesome.com/f4e088b372.js" crossorigin="anonymous"></script>

</head>

<body>

    <!-- 게시글 목록 공통 화면 -->

    <!-- header -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <c:if test="${!empty param.query}" >
        <c:set var="qs" value="&keyword=${param.keyword}&query=${param.query}" />
    </c:if>

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
            
            <!-- 검색창 --> 
            <form action="${cateCode}" method="GET" id="boardSearch">
                <div class="search-area">
                    <select name="keyword" id="searchKey">
                        <option value="t">제목</option>
                        <option value="c">내용</option>
                        <option value="tc">제목+내용</option>
                        <c:if test="${cateCode == 18}">
                            <option value="w">작성자</option>
                        </c:if>
                    </select>

                    <input type="search" name="query" id="searchQuery" placeholder="검색어 입력">

                    <button id="searchBtn"><i class="fa-solid fa-magnifying-glass"></i></button>
                </div>
            </form>

            <!-- 게시글 목록 -->
            <section class="board-list">
            
                <div class="list-wrapper">
                    <table class="list-table">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>조회수</th>
                            </tr>
                        </thead>

                        <tbody>
                            <!-- 게시글 조회 시 글 목록 존재 X -->
                            <c:choose>
                                <c:when test="${empty boardList}">
                                    <tr>
                                        <th colspan="5">게시글이 존재하지 않습니다.</th>
                                    </tr>
                                </c:when>

                                <c:otherwise>
                                    <c:forEach var="board" items="${boardList}">
                                        <tr>
                                            <!-- 게시글] 조회 시 글 목록 존재 O -->
                                            <td>${board.boardNo}</td>
                                            <td>
                                                <c:if test="${board.boardSecretFlag == 'Y'}">
                                                    <i class="fa-solid fa-lock"></i> 
                                                </c:if>
                                                <a href="/board/${cateCode}/${board.boardNo}?cp=${pagination.currentPage}${qs}">${board.boardTitle}</a>
                                                <c:if test="${cateCode == 16 || cateCode == 18}">
                                                    [${board.commentCount}]
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${cateCode == 16}">
                                                        ${fn:substring(board.memberName, 0,1)}*<c:if test="${fn:length(board.memberName) > 2}">${fn:substring(board.memberName, 2, fn:length(board.memberName))}</c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${board.memberName}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${board.boardUpdateDate != null}">
                                                        ${board.boardUpdateDate}
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${board.boardCreateDate}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${board.readCount}</td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <div class="btn-area">
                    <c:choose>
                        <%-- 공지사항 : 관리자에게만 보임 --%>
                        <c:when test="${cateCode == 15}">
                            <c:if test="${loginMember.authority == 2}">
                                <button id="writeBtn">글쓰기</button>
                            </c:if>
                        </c:when>
                        
                        <c:otherwise>
                            <%-- 회원만 글쓰기 가능 --%>
                            <c:if test="${loginMember.authority == 1}">
                                <button id="writeBtn">글쓰기</button>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- pagination -->
                <div class="pagination-area">

                    <ul class="pagination">
                        <!-- 첫 페이지로 이동 : << -->
                        <li><a href="/board/${cateCode}?cp=1${qs}">&lt;&lt;</a></li>

                        <!-- 이전 목록 마지막 페이지로 이동 : < -->
                        <li><a href="/board/${cateCode}?cp=${pagination.prevPage}${qs}">&lt;</a></li>

                        <!-- 특정 페이지로 이동 -->
                        <!-- 1페이지 씩 이동 -->
                        <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
                            <c:choose>
                                <c:when test="${i == pagination.currentPage}">
                                    <%-- 현재 페이지 --%>
                                    <li><a class="current">${i}</a></li>
                                </c:when>

                                <c:otherwise>
                                    <%-- 현재 페이지 제외한 나머지 --%>
                                    <li><a href="/board/${cateCode}?cp=${i}${qs}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                  

                        <!-- 다음 목록 시작 페이지로 이동 : > -->
                        <li><a href="/board/${cateCode}?cp=${pagination.nextPage}${qs}">&gt;</a></li>

                        <!-- 마지막 페이지로 이동 : >> -->
                        <li><a href="/board/${cateCode}?cp=${pagination.maxPage}${qs}">&gt;&gt;</a></li>
                    </ul>

                </div>
               
            </section>
        </section>


    </section>

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />


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

    <!-- boardList.js 연결 -->
    <script src="/resources/js/board/boardList.js"></script>

</body>
</html>