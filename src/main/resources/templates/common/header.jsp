<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	(()=>{
      var link = document.createElement('link');
      link.rel ="shortcut icon";
      link.href ="/resources/images/logo.png";
      document.head.appendChild(link);
	})();
</script>

<header>
	<nav>
	    <ul>
	        <li><a href="/"><img id="homeLogo" src="/resources/images/logo.png">자바 라이브러리</a></li>
	        <li>
				<a href="/intro/1">도서관 안내</a>
				<ul>
					<li><a href="/intro/1/7">도서관 소개</a></li>
					<li><a href="/intro/1/8">이용안내</a></li>
					<li><a href="/intro/1/9">시설안내</a></li>
				</ul>
			</li>
	        <li>
				<a href="/book/1/1">자료 검색</a>
				<ul>
					<li><a href="/book/1/1">통합 자료 검색</a></li>
					<li><a href="/book/2">희망 도서 신청</a></li>
				</ul>
			</li>
	        <li>
				<a href="/scheduling/12">참여마당</a>
				<ul>
					<li><a href="/scheduling/12">교육문화 프로그램</a></li>
					<li><a href="/scheduling/13">열람실</a></li>
					<li><a href="/scheduling/14">세미나실</a></li>
				</ul>
			</li>
	        <li>
				<a href="/board/15">이용자 마당</a>
				<ul>
					<li><a href="/board/15">공지사항</a></li>
					<li><a href="/board/16">문의사항</a></li>
					<li><a href="/board/17">자주 묻는 질문</a></li>
					<li><a href="/board/18">책 후기 나눠요</a></li>
				</ul>
			</li>
	        <li>
				<a href="/food">맛있는 도서관</a>
				<ul>
					<li><a href="/food">구내식당</a></li>
					<li><a href="/cafe">카페</a></li>
				</ul>
			</li>
	        <li>
				<a href="/myLibrary/book" onclick="alertMsg()">내 서재</a>
				<ul>
					<li><a href="/myLibrary/book" onclick="alertMsg()">나의 도서관</a></li>
					<li><a href="/myPayment" onclick="alertMsg()">결제내역</a></li>
					<li><a href="/myPage/info" onclick="alertMsg()">회원정보</a></li>
				</ul>
			</li>
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
						<c:if test="${loginMember.authority == 1}">
							<a href="/myPage/info">내 정보</a>
						</c:if>

						<c:if test="${loginMember.authority == 2}">
							<a href="/admin/1">관리자</a>
						</c:if>

                        <a href="/member/logout">로그아웃</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
	</nav>
	<div></div>
</header>

<c:set var="requestAddr" value="${requestScope['javax.servlet.forward.servlet_path']}"/>
<c:set var="reqAddr" value="${fn:split(requestAddr, '/')}" scope="request"/>

<c:if test="${requestAddr != '/'}">
    <div class="library-book">
    	<img src="/resources/images/library_book.png">
    </div>
</c:if>

<script src="https://kit.fontawesome.com/58046189b2.js" crossorigin="anonymous"></script>
<script>
	const loginMember = "${loginMember}";

	function alertMsg(){
		if(loginMember == ""){
			alert("로그인 후 이용해주세요.");
		}
	}
</script>