<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Java Library - 인사말</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/lib_greeting.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
            <div class="content-title">인사말</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
                <div id="greeting_book">
                </div>
                <p>
                    책과 함께 행복한 인생을 만드는 Java Library 홈페이지를 <br>
                    방문해주신 여러분을 진심을 환영합니다. <br>
                    <br>
                     Java Library는 조용히해조의 협력으로 개관하여 <br>
                    여러분을 맞이할 수 있게 되었습니다. <br>
                    
                     시민들의 서재로서 누구나  편안하게 이용할 수 있는 도서관으로 나아가기 위해 <br>
                    최선의 노력을 기울이고 있습니다. <br>
                    <br>
                     시민들의 지식 정보 수준 향상과 문화 요구 충족에 도움을 줄 수 있는 <br>
                    친숙하고 편안한 도서관으로 거듭나기 위해 최선의 노력을 다하겠습니다. <br>
                    <br>
                     여러분의 많은 관심과 사랑을 부탁드립니다. <br>
                    <br>
                    감사합니다. <br>
                    <br>
                    <strong>조용히해조 조장</strong> <br>

                </p>
            </section>
		
	</section>
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>