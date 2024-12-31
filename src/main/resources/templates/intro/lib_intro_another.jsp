<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="libTitle" value="${another.libTitle }"/>
<c:set var="board" value="${another.board}"/>
<c:set var="libAdd" value="${another.libAdd}"/>
<%-- 가져온 값 변수에 담음 --%>
<meta charset="UTF-8">
<title>Java Library - 주변 도서관</title>

<link rel="stylesheet" href="/resources/css/main-style.css">
<link rel="stylesheet" href="/resources/css/common/sideMenu.css">
<link rel="stylesheet" href="/resources/css/intro/popup.css">
<link rel="stylesheet" href="/resources/css/intro/del-popup.css">
<link rel="stylesheet" href="/resources/css/intro/intro_another.css">
<link rel="stylesheet" href="/resources/css/intro/edit_popup.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="main-content-suround-section">
		<jsp:include page="/WEB-INF/views/intro/sideMenu.jsp"></jsp:include>
		
		<section class="content-suround-section">
			<div class="content-title">주변 도서관</div>
            <div class="title-line"></div>

            <section class="content-detail-section">
            
            <c:if test="${empty libTitle}">
            	<div>
            		주변 도서관이 없습니다.
            	</div>
            </c:if>
            
            <c:if test="${!empty libTitle}">
                <nav class="library-another-btn-area">
					<c:forEach items="${libTitle}" var="title"> 
						<c:if test="${title.BOARD_NO == board.boardNo}">
							<div class="libraryAnother current"> <a href="/intro/1/7/28/${title.BOARD_NO}" class="current">${title.BOARD_TITLE}</a> </div>
						</c:if>

						<c:if test="${title.BOARD_NO != board.boardNo}">
							<div class="libraryAnother"> <a href="/intro/1/7/28/${title.BOARD_NO}">${title.BOARD_TITLE}</a> </div>
						</c:if>

					</c:forEach>

                </nav>
                
                <article class="map-content-suround">
					
                    <div class="introduce-another-library-area">
						<table class="introduce-another-content">
							<tr>
								<th>주소</th>
								<td id="anLibraryAddress">${libAdd.libraryAddress}</td>
							</tr>
							<tr>
								<th>소개</th>
								<td id="anLibraryIntro">${board.boardContent}</td>
							</tr>
						</table>

                    </div>
                    <div id="map"></div>
                </article>
                
				<c:if test="${!empty board.imageList}">
					<article class="library-another-images-area">
						<div class="boardImg">
							<c:forEach items="${board.imageList}" var="img">
								<img src="${img.imagePath}${img.imageReName}" />
							</c:forEach>
						</div>
					</article>
				</c:if>

				<!-- 검색한 지도의 위도, 경도 저장 -->
				<input type="hidden" name="libraryLat" value="${libAdd.searchLat}">
				<input type="hidden" name="libraryLng" value="${libAdd.searchLng}">
				
				</c:if>

				<c:if test="${loginMember.authority == 2 }">
					<div class="manager-btn-area">
						<button onclick="openEditModal(this)" id="insertLibrary">추가</button>
						<c:if test="${!empty libTitle}">
							<button onclick="openUpdateModal(this,${board.boardNo})" id="updateLibrary">수정</button>
							<button onclick="openModal(this)" id="deleteLibrary">삭제</button>
						</c:if>
					</div>
            </c:if>
               
            </section>
		</section>		
	</section>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	<!-- 모달 영역 -->
	
	<!-- 편집 모달 -->
    <div id="edit-popup_layer">
	  <div class="edit-popup_box">
	  	  <div class="edit-popup_header">
	  	  	<p>주변 도서관 편집</p>
			<span id="edit-popup-close">&times;</span>
	  	  </div>
	  	  
	      <!--팝업 컨텐츠 영역-->
	      <div class="edit-popup_content">

			<form action="/intro/1/7/28/insert" method="post" name="editIntroFrm" enctype="multipart/form-data" id="editIntroFrm">
				<div>
					<div class="popupTitle">
						<span>도서관명</span>
						<input type="text" name="boardTitle">
					</div>

					<div class="popupAddress">
						<span>주소</span>
						<input type="text" name="libraryAddress" id="libraryAddress">
						<button type="button" id="searchMap">검색</button>
					</div>

					<div class="popup-map-suround">
						<div id="map2"></div>
					</div>

					<div class="popupIntroSuround">
						<div>소개</div>
						<textarea name="boardContent"></textarea>
					</div>

					<div class="pop-image-suround">
						
						<div class="boardImage">
							<label for="img1">
								<img class="preview" src="">
							</label>

							<input type="file" class="inputImage" id="img1" accept="image/*" name="images">
							<span class="delete-image">&times;</span>
						</div>

						<div class="boardImage">
							<label for="img2">
								<img class="preview" src="">
							</label>

							<input type="file" class="inputImage" id="img2" accept="image/*" name="images">
							<span class="delete-image">&times;</span>
						</div>

						<div class="boardImage">
							<label for="img3">
								<img class="preview" src="">
							</label>

							<input type="file" class="inputImage" id="img3" accept="image/*" name="images">
							<span class="delete-image">&times;</span>
						</div>

						<div class="boardImage">
							<label for="img4">
								<img class="preview" src="">
							</label>

							<input type="file" class="inputImage" id="img4" accept="image/*" name="images">
							<span class="delete-image">&times;</span>
						</div>
					</div>

					<!-- 검색한 지도의 위도, 경도 저장 -->
					<input type="hidden" name="searchLat" value="">
					<input type="hidden" name="searchLng" value="">

					<!-- 기존 이미지가 있다가 삭제된 이미지의 순서를 기록 -->
					<input type="hidden" name="deleteList" value="">
					
				</div>
				<!--팝업 버튼 영역-->
				<div class="edit-popup_btn">
					<button id="edit-confirm_btn">등록</button>
				</div>
			</form>

		</div>

	  </div>
	</div>
	
	<!-- 삭제 알림창 모달-->
	<div id="del-popup_layer">
		<div class="del-popup_box">
			  <div class="del-popup_header">
				  <p>주변 도서관 삭제</p>
			  </div>
			  
			<!--팝업 컨텐츠 영역-->
			<div class="del-popup_content">
				<p> 정말 삭제 하시겠습니까?
				</p>
			</div>
			<!--팝업 버튼 영역-->
			<div class="del-popup_btn">
				<button id="del-confirm_btn" onclick="deleteLibAn(${board.boardNo})">확인</button>
				<button id="del-cancel_btn">취소</button>
			</div>
		</div>
	  </div>

	  <!--알림창 모달-->
	<div id="popup_layer">
		<div class="popup_box">
			  <div class="popup_header">
				  <p>주변 도서관 편집</p>
			  </div>
			  
			<!--팝업 컨텐츠 영역-->
			<div class="popup_content">
				<p></p>
			</div>
			<!--팝업 버튼 영역-->
			<div class="popup_btn">
				<button id="confirm_btn">확인</button>
			</div>
		</div>
	  </div>
	  
	  <!-- 사진 모달 -->
	<div class="modal">
        <span id="modal-close">&times;</span>
        <img src="" id="modal-image">
    </div>

	
	<script>
		const boardNo = "${board.boardNo}";
		let message = "${introMsg}";
	</script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=607938550ca3e7059ff2ce14439ad035&libraries=services"></script>
    <script src="/resources/js/intro/lib_intro_another.js"></script>
	
</body>
</html>