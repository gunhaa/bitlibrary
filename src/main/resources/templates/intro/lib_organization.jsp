<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 조직도</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/lib_organization.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
			<div class="content-title">조직도</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                
                <section class="organWrap">
                    <h4>조용히해조</h4>
                    <div class="organList cols2">
                      <ul>
                        <li><div class="box">
                          <h5>조장</h5>
                          <ul>
                            <li>도서관 안내</li>
                            <li>이용 안내</li>
                            <li>시설 안내</li>
                          </ul>
                        </div></li>
                        <li><div class="box">
                          <h5>부조장</h5>
                          <ul>
                            <li>내 서재</li>
                            <li>로그인</li>
                            <li>회원가입</li>
                            <li>관리자 페이지</li>
                          </ul>
                        </div></li>
                        <li><div class="box">
                          <h5>체육단장</h5>
                          <ul>
                            <li>공지사항</li>
                            <li>문의사항</li>
                            <li>리뷰게시판</li>
                            <li>자주 묻는 질문</li>
                          </ul>
                        </div></li>
                        <li><div class="box">
                          <h5>응원단장</h5>
                          <ul>
                            <li>도서검색</li>
                            <li>도서예약</li>
                            <li>도서 즐겨찾기</li>
                          </ul>
                        </div></li>
                        
                        <li><div class="box">
                          <h5>서기관</h5>
                          <ul>
                            <li>클래스 안내</li>
                            <li>장소 예약</li>
                            <li>열람실 예약</li>
                          </ul>
                        </div></li>
                        <li><div class="box">
                          <h5>깜찍이</h5>
                          <ul>
                            <li>식당</li>
                            <li>카페</li>
                            <li>식권 결제</li>
                            <li>음료 결제</li>
                          </ul>
                        </div></li>
                      </ul>
                    </div>
                  </section>
            </section>
			
      </section>
		
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>