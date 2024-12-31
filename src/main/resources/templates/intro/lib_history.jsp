<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 연혁</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/lib_history.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
            <div class="content-title">연혁</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div>
                    <ul class="library-history">
                        <li>
                            <h4 class="year">2024</h4>
                            <dl>
                                <dt class="date">04.11</dt>
                                <dd class="info">도서관 개관</dd>
                            </dl>
                            <dl>
                                <dt class="date">05.17</dt>
                                <dd class="info">프로그래밍언어 응용 | 네트워크 프로그래밍 구형 | 프로그래밍언어 활용</dd>
                            </dl>
                            <dl>
                                <dt class="date">06.05</dt>
                                <dd class="info">SQL 활용 | 데이터베이스 구현 | SQL 응용</dd>
                            </dl>
                            <dl>
                                <dt class="date">06.24</dt>
                                <dd class="info">UI 구현 | UI 디자인</dd>
                            </dl>
                            <dl>
                                <dt class="date">07.15</dt>
                                <dd class="info">서버프로그램 구현 | 애플리케이션 설계 | 인터페이스 구현</dd>
                            </dl>
                            <dl>
                                <dt class="date">08.06</dt>
                                <dd class="info">통합 구현</dd>
                            </dl>
                            <dl>
                                <dt class="date">08.14</dt>
                                <dd class="info">프로젝트 기반 공공데이터 아키텍쳐 설계 | 프로젝트 기반 공공데이터 활용</dd>
                            </dl>
                            <dl>
                                <dt class="date">09.05</dt>
                                <dd class="info">요구사항 확인 | 화면 구현</dd>
                            </dl>
                            <dl>
                                <dt class="date">09.13</dt>
                                <dd class="info">애플리케이션 테스트 수행</dd>
                            </dl>
                            <dl>
                                <dt class="date">09.23</dt>
                                <dd class="info">애플리케이션 배포</dd>
                            </dl>
                            <dl>
                                <dt class="date">09.26</dt>
                                <dd class="info">도서관 휴식</dd>
                            </dl>
                        </li>
                        
                    </ul>
                </div>
                
            </section>
            </section>
            
		
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>