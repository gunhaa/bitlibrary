<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 도서관 소개</title>
	
<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/lib_intro.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
			<div class="content-title">도서관 오시는 길</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div>
                   <table class="library-introduce-table">
                        <tr>
                            <td>주소</td>
                            <td>강남지원 1관 : 서울특별시 강남구 테헤란로 14길 6 남도빌딩</td>
                        </tr>
                        <tr>
                            <td>버스</td>
                            <td>역삼역.포스코P&S타워 정류장</td>
                        </tr>
                        <tr>
                            <td>지하철</td>
                            <td>지하철 2호선 역삼역 3번출구 100m</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>지하철 2호선 강남역 1번출구 약700m</td>
                        </tr>
                   </table>
                </div>
                
                <div class="kakao-map-suround">
                	<div id="map" style="width:500px;height:400px;"></div>
                	
                </div>
                
            </section>
      </section>
    </section>
	
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=607938550ca3e7059ff2ce14439ad035&libraries=services,drawing"></script>
	<script src="/resources/js/intro/lib_intro.js"></script>

</body>
</html>