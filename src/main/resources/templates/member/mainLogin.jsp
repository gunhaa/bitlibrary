<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script src="https://kit.fontawesome.com/cb5f1fee4d.js" crossorigin="anonymous"></script>

<section class="main-login-area">
    <c:if test="${empty loginMember}">
        <article class="main-login-title">회원 로그인</article>
        <form action="/member/login" method="POST" class="main-login-form">

                <input type="text" name="memberEmail" placeholder="아이디 입력" value="${cookie.saveId.value}"/>
                <input type="password" name="memberPw" placeholder="비밀번호 입력"/>
                <div class="main-saveId">
                    <c:if test="${!empty cookie.saveId.value}">
                        <c:set var="save" value="checked"/>
                    </c:if>
                    <input type="checkbox" name="saveId" id="saveId" ${save}/>
                    <label for="saveId">아이디 저장</label>
                </div>
                <div class="main-login-btn-area">
                    <button id="main-login-btn">로그인</button>
                </div>

            <div class="main-my-area">
                <a href="/member/findId"><span>ID 찾기</span></a>
                <span>|</span>
                <a href="/member/resetPw"><span>비밀번호 초기화</span></a>
                <span>|</span>
                <a href="/member/signUp"><span>회원가입</span></a>
            </div>
        </form>
    </c:if>
    <c:if test="${!empty loginMember}">
        <article class="main-login-title main-login-member-title"><strong>${loginMember.memberName}</strong>님 환영합니다.</article>
        <article class="main-login-form main-login-table">
            <table>
                <tr>
                    <td>도서대출현황</td>
                    <td>${loginMember.loanBook}</td>
                </tr>
                <tr class="second-tr">
                    <td>도서예약현황</td>
                    <td>${loginMember.resvBook}</td>
                </tr>
                <tr>
                    <td>연체도서</td>
                    <td>${loginMember.overdueBook}</td>
                </tr>
            </table>
            <div class="main-login-btn-area main-logout-btn-area">
                <div id="main-my-library-btn"><a href="/member/logout">로그아웃</a></div>
                <div id="main-my-library-btn"><a href="/myLibrary/book">내 서재</a></div>
            </div>

        </article>
    </c:if>
    
</section>

<script src="/resources/js/member/mainLogin.js" defer></script>