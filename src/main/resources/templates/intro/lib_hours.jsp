<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 이용시간</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/lib_hours.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
            <div class="content-title">이용시간</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div>
                    <h3>도서관 이용 시간</h3>
                    <table>
                        <thead>
                            <tr>
                                <th rowspan="2">실별</th>
                                <th colspan="2">이용시간</th>
                                <th rowspan="2">휴관일</th>
                                <th rowspan="2">비고</th>
                            </tr>
                            <tr>
                                <th>평일</th>
                                <th>주말</th>
                            </tr>

                        </thead>
                        <tbody>
                            <tr>
                                <td>문헌정보자료실</td>
                                <td>09:00 ~ 20:00</td>
                                <td>09:00 ~ 18:00</td>
                                <td rowspan="4">매주 금요일 <br> 및 법정 공휴일</td>
                                <td rowspan="4">이용 기간 및<br> 휴관은 도서관 <br> 운영 여건에  따라 변경될 수 있습니다.</td>
                            </tr>
                            <tr>
                                <td>열람실</td>
                                <td>07:00 ~ 23:00</td>
                                <td>07:00 ~ 23:00</td>
                            </tr>
                            <tr>
                                <td>식당 + 매점</td>
                                <td>09:00 ~ 20:00</td>
                                <td>09:00 ~ 20:00</td>
                            </tr>
                            <tr>
                                <td>열람실</td>
                                <td>09:00 ~ 20:00</td>
                                <td>09:00 ~ 20:00</td>
                            </tr>

                        </tbody>

                    </table>
                    <h3>휴관일</h3>
                    <ul>
                        <li>매주 금요일, 신정, 설날, 추석 연휴, 공휴일</li>
                        <li>특별한 사유로 지정한 날</li>
                    </ul>
                    <p> <strong>※ 휴관일, 공휴일은 행사,클래스는 하지 않습니다. ※</strong></li>
                    
                </div>
            </section>
		</section>
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>


</body>
</html>