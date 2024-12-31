<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Java Library</title>
<link rel="stylesheet" href="/resources/css/search/search-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/main-style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<section class="main">
		<jsp:include page="/WEB-INF/views/search/sideMenu.jsp"></jsp:include>

		<section class="main-content">
			<div class="main-title">상세 검색</div>
			<hr class="titleLine"/>
			<section class="body-content">
				<div class="searchFormDetail">
					<div class="detailItem">
						<div class="details">검색 항목</div>
						<div class="detailsYear">발행 연도</div>
						<div class="detailsCount">출력 건수</div>
						<div class="detailsAlign"></div>

					</div>
					<div class="detailInput">

						<div class="searchDetail">
							<div class="searchTitle">
								<div class="bookTitle">서명</div>
								<div class="input">
									<input type="text" class="inputDetail" id="inputDetail" placeholder="책 제목을 입력해주세요."></input>
								</div>
							</div>

							<div class="searchAuthor">
								<div class="author">저자</div>
								<div class="input">
									<input type="text" class="inputDetail" id="inputDetail" placeholder="저자 이름을 입력해주세요."></input>
								</div>
							</div>

							<div class="searchPublisher">
								<div class="publisher">출판사</div>
								<div class="input">
									<input type="text" class="inputDetail" id="inputDetail" placeholder="출판사명을 입력해주세요."></input>
								</div>
							</div>

							<div class="searchYear">
								<div class="inputYear">
									<input type="text" class="inputDetail" id="inputYear"></input> 
									<span>부터 </span>
									<input type="text" class="inputDetail" id="inputYear"></input>
								</div>
							</div>

							<div class="searchPrint">
								<select id="searchPrint" name="searchPrint" title="출력건수">
									<option value="10">10건</option>
									<option value="20">20건</option>
									<option value="30">30건</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-area">
					<div class="index-btn">
						<button id="index-btn">검색</button>
					</div>

					<div class="refresh-btn">
						<button id="refresh-btn">초기화</button>
					</div>
				</div>
				<div id="queryResult"></div>
			</section>
		</section>
	</section>
	<jsp:include page="/WEB-INF/views/search/searchModal.jsp"></jsp:include>
    <script>
        const loginMemberNo = "${loginMember.memberNo}";
    </script>
    <script src="/resources/js/search/search.js"></script>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>