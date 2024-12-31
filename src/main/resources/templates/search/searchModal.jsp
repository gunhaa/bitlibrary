<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/css/common/modal.css">
<div id="popup_layer">
	<div class="popup_box_res">
		<div class="popup_header">
		<!-- 안에 p 태그로 함 -->
		<p>도서 예약</p>
		</div>
		
		<!--팝업 컨텐츠 영역-->
		<div class="popup_content">
		<!-- 안에 p태그로 함 -->
		<div class="popup_content_top">
			<div id="message_box"></div>
			<div class="popup_content_bar"></div>
		</div>
		

		<div class="popup_content_mid">
			<div class="popup_content_left">
				<img src="/resources/images/bookThumbnail" />
			</div>
			<div class="popup_content_right">
				<h2>도서 상세</h2> 
				<p>서명 : safsf</p>
				<p>저자 : </p>
				<p>발행처 : </p>
				<p>발행년도 : </p>
				<p>ISBN: </p>
			</div>
		</div>
	
		
		<!--팝업 버튼 영역-->
		<div class="popup_btn">
			<button id="confirm_btn">확인</button>
			
			<button id="cancel_btn">취소</button>
		</div>
		</div>
		
	</div>
</div>