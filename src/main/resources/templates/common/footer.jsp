<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<footer>
        <article>
            <a href="#">개인정보처리방침</a>    
            <span>|</span>
            <a href="#"> 영상정보처리기 운영·관리방침 </a>    
        </article>
        <div>
            <img src="/resources/images/logo.png" >
            <div>
                <p>서울특별시 강남구 테헤란로 14길 6 (남도빌딩) </p>
                <p> 전화번호 : 1544-9970 </p>
                <p> 이용시간 : 화~금 07:00 ~ 23:00 / 토,일 07:00~21:00 / 금요일, 공휴일 휴관</p>
            </div>
            <img src="/resources/images/wa_logo.png" >
        </div>
</footer>

<c:if test="${!empty message}">

    <script>
        alert('${message}');
    </script>


</c:if>