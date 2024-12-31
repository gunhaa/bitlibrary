<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 도서관 지도</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/lib_map.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
			<div class="content-title">시설 안내</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div>
                    <h3>층별 안내</h3>
                    <div class="suround-map">
                        <div>
                            <div class="map-btn ">B1</div>
                            <div class="map-btn current">1층</div>
                            <div class="map-btn">2층</div>
                            <div class="map-btn">3층</div>
                            <div class="map-btn">4층</div>
                        </div>

                        <div class="real-map">
                            <img src="/resources/images/intro/library_map_1B.png" >
                        </div>

                        <div class="real-map current">
                            <img src="/resources/images/intro/library_map_1F.png" >
                        </div>

                        <div class="real-map">
                            <img src="/resources/images/intro/library_map_2F.png" >
                        </div>

                        <div class="real-map">
                            <img src="/resources/images/intro/library_map_3F.png" >
                        </div>

                        <div class="real-map">
                            <img src="/resources/images/intro/library_map_4F.png" >
                        </div>

                    </div>
                </div>
            </section>
		</section>
		</section>
		
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script src="/resources/js/intro/lib_map.js"></script>

</body>
</html>