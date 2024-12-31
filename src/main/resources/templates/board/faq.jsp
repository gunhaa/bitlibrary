<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html lang="en">

<c:forEach items="${category}" var="catelist">
    <c:if test="${catelist.CAT_CODE == cateCode}">
        <c:set var="cateName" value="${catelist.CAT_NAME}"/>
    </c:if>
</c:forEach>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Library - ${cateName}</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/board/faq-style.css">
    <script src="js/jquery-3.7.1.min.js"></script>
    <script src="https://kit.fontawesome.com/f4e088b372.js" crossorigin="anonymous"></script>
    
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
            <div class="content-title">자주 묻는 질문</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div class="faq-container">

                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 도서관 운영시간이 어떻게 되나요?
                    </div>

                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i> 
                        &nbsp;자바 라이브러리의 휴관일은 <b>매주 금요일 및 법정 공휴일</b>입니다. 휴관일을 제외한 자바 라이브러리의 운영시간은 <b>평일(월~목요일)</b>은 <b>오전 9시부터 오후 8시</b>까지이며,
                        <b>주말(토요일/일요일)</b>은 <b>오전 9시부터 오후 6시</b>까지 운영됩니다. 운영시간은 도서관의 상황에 따라 유기적으로 변동될 수 있으니 이 점 참고 바랍니다.
                    </p>

    
                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 도서 대출과 반납은 어디서 할 수 있나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;<b>2층</b>에 위치한 <b>문헌정보실</b>에서 대출과 반납을 할 수 있습니다. 또한 반납의 경우 <b>1층</b>에 위치한 <b>데스크</b>와 <b>근처 무인반납기</b>를 통해 반납을 하실 수 있습니다. 
                        <br>대출과 반납을 원하시는 이용자께서는 1층의 데스크에서 회원으로 등록하거나 혹은 사이트에서 회원가입 후에 이용하실 수 있으니 이 점 참고 바랍니다.
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 도서 대출 기한은 어떻게 되나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp; 대출은 <b>최대 2주</b>의 기한이 주어집니다. 반납일이 연체 됐을 경우 연체된 기간 만큼 도서 대출이 금지되오니 이 점 참고 바랍니다.
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 클래스 신청은 어디서 할 수 있나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;클래스 신청을 원하시는 회원께서는 <b>참여 마당</b> 카테고리를 통해 신청하실 수 있습니다. 
                        <br>회원은 <b>내 서재</b> 카테고리 내의 <b>나의 도서관</b>에서 클래스 신청과 예약 현황을 확인하실 수 있으니 이 점 참고 바랍니다.
                        단, <b>비회원</b>은 클래스 신청이 불가능하므로 원하시는 이용자께서는 회원가입 진행 후 신청해주시면 감사하겠습니다. 
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 희망 도서 신청은 어디서 할 수 있나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;희망도서 신청을 원하시는 회원은 <b>자료 검색</b> 카테고리를 통해 신청하실 수 있습니다.
                        <br>회원은 <b>내 서재</b> 카테고리 내의 <b>나의 도서관</b>에서 희망도서 신청 내역을 확인하실 수 있으니 이 점 참고 바랍니다.
                        <br>단, <b>비회원</b>은 희망도서 신청이 불가능하므로 원하시는 이용자께서는 회원가입 진행 후 신청해주시면 감사하겠습니다. 
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 도서관 내 식당과 카페 이용자에 제한이 있나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;제한은 없습니다. 다만, <b>사이트 내</b>에서 식권을 구매하거나 카페 메뉴를 결제하고자 하는 이용자들은 회원가입 진행 후 이용이 가능합니다.
                        <br>도서관에 직접 방문하시어 식당과 카페를 이용하실 이용자께서는 회원의 유무 상관없이 <b>도서관 지하 1층 매장</b>에서 결제하신 후에 자유롭게 이용이 가능합니다.
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 식권 결제는 어떻게 이루어지나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;식권 결제를 원하시는 회원께서는 <b>맛있는 도서관</b> 카테고리를 통해 식권을 구매한 후 도서관 내에서 식당을 이용하실 수 있습니다.
                        <br>도서관 내 식당에서도 <b>당일 식단표</b>를 제공하고 있지만, 사이트에서도 <b>주차별 식단표</b>와 <b>당일 식단표</b>도 알려드리고 있으니 미리 알고 싶은 이용자께서는 사이트에서 확인해주시면 감사하겠습니다. 
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 대관실 예약은 어떻게 할 수 있나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;대관실 예약을 원하시는 회원께서는 <b>참여 마당</b> 카테고리를 통해 예약하실 수 있습니다.
                        <br>회원은 <b>내 서재</b> 카테고리 내의 <b>나의 도서관</b>에서 대관실 예약 내역을 확인하실 수 있으니 이 점 참고 바랍니다.
                        <br>단, <b>비회원</b>은 대관실 예약이 불가능하므로 원하시는 이용자께서는 회원가입 진행 후 예약을 진행해주시면 감사하겠습니다. 
                    </p>


                    <div class="faq-question"><i class="fa-solid fa-q" style="color: rgb(19, 150, 202);"> .</i> 비회원도 열람실을 이용할 수 있나요?
                    </div>
                    <p class="faq-answer"><i class="fa-solid fa-a" style="color: rgb(247, 83, 54);"> .</i>
                        &nbsp;<b>이용하실 수 없습니다.</b> 열람실을 이용하실 이용자께서는 사이트 혹은 도서관에 방문하셔서 회원가입 진행 후 이용하실 수 있습니다. 
                        <br>열람실은 사이트 내의 <b>참여 마당</b> 카테고리에서 <b>도서관 예약시스템</b>을 통해 예약하실 수 있으며, <b>내 서재</b> 카테고리 내의 <b>나의 도서관</b>에서 열람실 예약 내역을 확인하실 수 있으니 이 점 참고 바랍니다.
                    </p>
                </div>
            </section>
        </section>


    </section>

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <script src="https://code.jquery.com/jquery-3.7.1.min.js" 
    integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script>

        const cateCode = "${cateCode}";
         
    </script>

    <script src="/resources/js/board/faq.js"></script>



</body>

</html>