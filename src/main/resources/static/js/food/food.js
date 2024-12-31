const popUpLayer = document.getElementById("popup_layer");

const payBtn = document.getElementById("pay-btn");
const popUpHeader = document.getElementsByClassName("popup_header");
const popUpContent = document.getElementsByClassName("popup_content");
const popupBox = document.getElementsByClassName("popup_box")[0];


if(payBtn != null){

	/* 식권 구매 모달 */
	payBtn.addEventListener("click", ()=> {
	
		popUpHeader[0].innerHTML= "<p>식권 구매</p>"
		popUpContent[0].innerHTML = `
										<div class="pay-meal-ticket">
											<div class="meal-ticket-amount">
												<p>식권</p>
												<div class="meal-ticket-btnArea">
													<input type="button" onclick='count("minus")' value='-' id="minusBtn" disabled='true'/>
													<div id="qty-result">0</div>
													<input type ="button" onclick='count("plus")' value='+' id="plusBtn"/>
												</div>
											</div>
											<hr/>
											<div class="meal-ticket-amount">
												<p>가격</p>
												<div id="price-result">0</div>
											</div>
										</div>
										<div class="popup_btn">
											<button id="confirm_btn">결제</button>
											<button id="cancel_btn" onclick="cancel()">취소</button>
										</div>
									`
		popUpLayer.style.display = 'block';
		  
		/* 동적으로 생성된 버튼에 이벤트 리스너 추가 */
		const confirmBtn = document.getElementById("confirm_btn");
	
		/* 결제 버튼임 */
		/* 결제 버튼 눌렀을 때 결제창 api 시작해야됨 */
		confirmBtn.addEventListener("click", ()=>{
			IMP.init('imp03608625');	// 아임포트 관리자 콘솔에서 확인한 '가맹점 식별코드' 입력
			IMP.request_pay(
				{
					pg: "kakaopay.TC0ONETIME", 	//pg사명 or pg사명.CID (잘못 입력할 경우, 기본 PG사가 띄워짐)
					pay_method : "card",
					merchant_uid : "merchant_" + new Date().getTime(),	// 가맹점 주문번호(아임포트를 사용하는 가맹점에서 중복되지 않은 임의의 문자열을 입력)
					name:"식권", //결제창에 노출될 상품명
					amount : price,	// 금액
					/* 
						buyer_email : "${loginMember.memberEmail}",
						buyer_name : "${loginMember.memberName}",
						buyer_tel : "${loginMember.memberPhoneNo}",
						buyer_addr : "${loginMember.memberAddress}"
					*/
					
				}, function(resp){	// callback
					var result = '';
					
					if(resp.success){

						const data = {
							memberNo : loginMemberNo,
							foodNo : 1,
							foodQty : qty
						}

						fetch("/food/pay",{
							method: "POST",
							headers: {"Content-Type" : "application/json"},
							body: JSON.stringify(data)
						})
						.then(resp=>resp.json())
						.then(res => {

							if(res > 0){
								popUpHeader[0].innerHTML= "<p>식권 구매</p>"
								popUpContent[0].innerHTML = `<div class="pay-meal-ticket">
																<p>구매가 완료되었습니다.</p>
															</div>
															<div class="popup_btn">
																<button id="agree_btn" onclick="agree()">확인</button>
															</div>
															`
								result='0';
								popUpLayer.style.display = 'block';
	
							}
						})
						.catch(err => console.log(err))					
	
					}else{
						popUpHeader[0].innerHTML= "<p>결제 실패</p>"
						popUpContent[0].innerHTML = `<div class="pay-meal-ticket">
														<p>${resp.error_msg}</p>
													</div>
													<div class="popup_btn">
														<button id="agree_btn" onclick="agree()">확인</button>
													</div>
													`
						result='1';
						popUpLayer.style.display = 'block';
					}
	
				}
			)
		})
	
	});
}



/* count 함수  */

let qty = 0;
let price = 0;

function count(type){
	const qtyResult = document.getElementById("qty-result");
	const priceResult = document.getElementById("price-result");

	if(qty >= 1){
		// 더하기/ 빼기
		if(type === "plus"){
			qty += 1;
			price = 6000 * qty;
		}else if(type === "minus"){
			qty -= 1;
			price = 6000 * qty;
		}

	}else{
		// 더하기/ 빼기
		if(type === "plus"){
			qty += 1;
			price = 6000 * qty;
		}
	}
 	
	if(qty>0){
		document.getElementById("minusBtn").disabled=false;
	}else{
		document.getElementById("minusBtn").disabled=true;
	}
	

	price = price.toLocaleString('ko-KR');
	// 결과 출력
	qtyResult.innerText = qty;
	priceResult.innerText = price;
}

/* 결제 완료 후 완료되었습니다. 했을 때 확인 버튼 */
function agree(){
	if(qty!=0 || price != 0){
		qty= 0;
		price=0;
		popUpLayer.style.display = 'none';
		window.location.href="/myPayment";
		
	}else{
		popUpLayer.style.display = 'none';
		window.location.reload();
	}
}


/* *************************************************************************** */
/* 관리자아아아아아ㅏ앙아ㅏ앙 */
const menuAddBtn = document.getElementById("menu-add-btn");
const menuUpdateBtn = document.getElementById("menu-update-btn");

/* 메뉴 추가 */
if(menuAddBtn != null){
	menuAddBtn.addEventListener("click", () => {
		popUpHeader[0].innerHTML= "<p>메뉴 추가</p>"
		popUpContent[0].innerHTML = `
								<form action="/food/insert" method="POST" enctype="multipart/form-data" name="menuAddFrm" id="menuAddFrm">
									<div class="menu-add-area">
										<div class="menu-name-area">
											<p>이름 :</p>
											<input type="text" name="menuName"  id="menuName"/>
										</div>
										<div class="image-area">
											<p>이미지 :</p>
											<div class="left-image-area">
												<label for="menuImage"> 
													<img class="preview">
												</label> 
												<i class="fa-solid fa-xmark" id="delete-image-btn"></i>
												<input type="file" class="inputImage" id="menuImage" accept="image/*" name="menuImage"> 
											</div>

											<div class="right-image-area">
<pre>
- 이미지 등록 
(권장사이즈 : 가로 200px X 세로 150px 혹은 4 : 3 비율)
- 파일양식 : jpg, jpeg, png(8MB 제한)
</pre>
												<div class="image-btn">
													<button type="button" id="image-add-btn">사진등록</button>
												</div>
											</div>
										</div>
									</div>
									<div class="popup_btn">
										<button type="submit" id="confirm_btn" class="menu_add">추가</button>
										<button id="cancel_btn" type="button" onclick="cancel()">취소</button>
									</div>
								</form>
									
									`
		
		popupBox.classList.add("menu-add-popupBox");
		popUpLayer.style.display = 'block';


		const preview = document.getElementsByClassName("preview")[0];
		const inputImage = document.getElementsByClassName("inputImage")[0];


		// 미리보기 삭제(x버튼)
		document.getElementById("delete-image-btn").addEventListener("click", e=>{

			// 미리보기 이미지가 있을 경우
			if(preview.getAttribute("src") != ""){

				preview.removeAttribute("src");

				inputImage.value="";
				$('#delete-image-btn').css('display', 'none');
			}
		})

		// 파일이 선택되거나, 선택 후 취소되었을 때
		$('.inputImage').on("change", e=>{
			const file = e.target.files[0]; // 선택된 파일의 데이터

			if(file != undefined){  // 파일이 선택되었을 때
				const reader = new FileReader();    // 파일을 읽는 객체

				reader.readAsDataURL(file);
				// 지정된 파일을 읽은 후 result에 URL 형식으로 저장

				reader.onload= e=>{ // 파일을 다 읽은 후 수행
					preview.setAttribute("src", e.target.result);
				}

				$('#delete-image-btn').css('display', 'block');

			}else{  // 선택 후 취소되었을 때
				// -> 선택된 파일 없음 -> 미리보기 삭제
				preview.removeAttribute("src");
				$('#delete-image-btn').css('display', 'none');
			}
		})


		/* 사진 등록 버튼 */
		$('#image-add-btn').on("click", ()=>{
			inputImage.click();
		})

		$('#confirm_btn').on("click", (e)=>{
			if(document.getElementById("menuName").value == 0 ){
				e.preventDefault();
				popupBox.classList.remove("menu-add-popupBox");
				popUpContent[0].innerHTML = `
					<p>메뉴 이름을 기입해주세요!</p>
					<div class="popup_btn">
						<button id="menu-add-btn" onclick="agree()">확인</button>
					</div>
				`
			}
		})
		
	})
}

if(addMenu != ""){
	popupBox.classList.remove("menu-add-popupBox");
	popUpLayer.style.display = 'block';
	if(addMenu != 0){
		menuAddSuccess();
	}else{
		menuAddFail();
	}
}


/* 메뉴 추가 성공 */
function menuAddSuccess(){
	popUpHeader[0].innerHTML= "<p>메뉴 추가</p>"
	popUpContent[0].innerHTML = `
				<p>메뉴 추가가 완료되었습니다.</p>
				<div class="popup_btn">
				<button id="agree_btn" onclick="agree()">확인</button>
				</div>
				`
}

/* 메뉴 추가 실패 */
function menuAddFail(){
	popUpHeader[0].innerHTML= "<p>메뉴 추가</p>"
	popUpContent[0].innerHTML = `
				<p>메뉴 추가를 실패하였습니다.</p>
				<div class="popup_btn">
				<button id="agree_btn" onclick="agree()">확인</button>
				</div>
				`
}

/* *************************************************************************** */

/* 메뉴 삭제 */
document.querySelectorAll('#menu-del-btn').forEach(button => {
    button.addEventListener('click', function() {
        const foodNo = this.getAttribute('foodNo');

		fetch("/food/del", {
			method: "PUT",
			headers : {"Content-Type" : "application/json"},
       	 	body: JSON.stringify(foodNo)
		})
		.then(resp=> resp.text())
		.then(result => {
			
			if(result>0){
				popupBox.classList.remove("menu-add-popupBox");
				popUpLayer.style.display = 'block';
				popUpHeader[0].innerHTML= "<p>메뉴 삭제</p>"
				popUpContent[0].innerHTML= `
												<p>정말 해당 메뉴를 삭제하시겠습니까?</p>
												<div class="popup_btn">
													<button id="agree_btn" onclick="agreeDel()">확인</button>
													<button id="cancel_btn" type="button" onclick="cancel()">취소</button>
												</div>
											`

				$('#cancel_btn').css("margin", "10px");
			}else{
				popupBox.classList.remove("menu-add-popupBox");
				popUpLayer.style.display = 'block';
				popUpHeader[0].innerHTML= "<p>메뉴 삭제</p>"
				popUpContent[0].innerHTML= `
												<p>메뉴 삭제에 실패하였습니다.</p>
												<div class="popup_btn">
													<button id="agree_btn" onclick="agree()">확인</button>
												</div>
											`
			}
		})
		.catch( e=> console.log(e))
    });
});

function cancel(){
	/* 이름이랑 image 삭제해야됨 */
	popUpLayer.style.display = 'none';
}

function agreeDel(){
	popupBox.classList.remove("menu-add-popupBox");
	popUpLayer.style.display = 'block';
	popUpHeader[0].innerHTML= "<p>메뉴 삭제</p>"
	popUpContent[0].innerHTML=	 `
									<p>삭제되었습니다!</p>
									<div class="popup_btn">
										<button id="agree_btn" onclick="agree()">확인</button>
									</div>
								`
}


/* *************************************************************************** */
/* 메뉴 수정 */
/* 백반 */
if(menuUpdateBtn != null){
	$('#menu-update-btn').on("click", ()=>{
		popupBox.classList.add("menu-add-popupBox");
		popUpLayer.style.display = 'block';
		popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
		popUpContent[0].innerHTML = `
									<form action="/food/insert" method="POST" enctype="multipart/form-data" name="menuUpdateFrm" id="menuUpdateFrm">
										<div class="menu-type-area">
											<p>식사종류 : </p>
											<div class="menu-type-btn-area">
												<input type="radio" id="foodType0" name="foodType" value="0" checked/>
												<label for="foodType0" class="transition">백반</label>
											</div>
										</div>
										<hr>
										<div class="menu-update-area">
											<div class="food-table-area">
												<table>
													<thead>
														<tr>
															<td>월</td>
															<td>화</td>
															<td>수</td>
															<td>목</td>
															<td>금</td>
															<td>토</td>
															<td>일</td>
														</tr>
													</thead>
													<tbody>
													</tbody>	
												</table>
											</div>
										</div>
										<div class="popup_btn">
											<button type="submit" id="confirm_btn" class="menu_update">수정</button>
											<button id="cancel_btn" type="button" onclick="cancel()">취소</button>
										</div>
									</form>
									`

		mealFoodFrm();
		popupBox.classList.add("menu-add-popupBox");
		popUpLayer.style.display = 'block';

		/* Checkbox change event */
		$('input[name="foodType"]').change(function() {
			// 모든 radio를 순회한다.
			$('input[name="foodType"]').each(function() {
				var checked = $(this).prop('checked');  // jQuery 1.6 이상 (jQuery 1.6 미만에는 prop()가 없음, checked, selected, disabled는 꼭 prop()를 써야함)

				if(checked){
					mealFoodFrm();

				}
			});
		});

		$("input:radio[name=foodType]").on("change", () => {
			if($("input:radio[name=foodType]:checked").val()=='0'){
				mealFoodFrm();
			}
		});
	})
}

const menuUpdateArea= document.getElementsByClassName('menu-update-area');

function mealFoodFrm(){
	menuUpdateArea[0].innerHTML = `
									<div class="food-table-area">
										<table>
											<thead>
												<tr>
													<td>월</td>
													<td>화</td>
													<td>수</td>
													<td>목</td>
													<td>금</td>
													<td>토</td>
													<td>일</td>
												</tr>
											</thead>
											<tbody>
											</tbody>	
										</table>
									</div>
								`;

	tbody = document.querySelector("tbody");

	fetch("/food/showMeal?typeNo=0")
	.then(resp => resp.json())
	.then(result => {

		tbody.innerHTML="";

		let value = "";
		
		result.forEach((food, index)=>{

			// 매 6번째 행 시작
			if (index % 6 == 0) {	
				value += '<tr>';
			}
		
			// 네 번째 아이템일 때 '휴관일' 추가
			if (index === 4) {
				value += '<td rowspan="5" class="holiday">휴관일</td>';
			}
		
			// 음식 이름 추가
			value += `<td class="calMenu" onclick="menuChangeModal(${food.foodNo}, '${food.foodName}')">${food.foodName}</td>`;
		
			// 매 6번째 행 끝
			if (index % 6 == 5) {
				value += '</tr>';
			}
		})

		tbody.innerHTML = value;
	})
	.catch(err=> console.log(err))
}


/* 백반... */
function updateMenu(no){
	const newMenuName = $('#newMenuName').val();
	fetch("/food/updateMeal", {
		method: "PUT",
		headers : {"Content-Type" : "application/json"},
		body: JSON.stringify({
			foodNo : no,
			foodName : newMenuName
		})
	})
	.then(resp => resp.text())
	.then(result => {
		if(result>0){
			popupBox.classList.remove("menu-add-popupBox");
			popUpLayer.style.display = 'block';
			popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
			popUpContent[0].innerHTML= `
											<p>메뉴가 수정되었습니다!</p>
											<div class="popup_btn">
												<button id="agree_btn" onclick="agree()">확인</button>
											</div>
										`
			
			$('#cancel_btn').css("margin", "10px");
		}else{
			popupBox.classList.remove("menu-add-popupBox");
			popUpLayer.style.display = 'block';
			popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
			popUpContent[0].innerHTML= `
											<p>메뉴 수정에 실패하였습니다.</p>
											<div class="popup_btn">
												<button id="agree_btn" onclick="agree()">확인</button>
											</div>
										`
		}
	})
	.catch(err => console.log(err))
}

function menuChangeModal(no, name){
	popupBox.classList.remove("menu-add-popupBox");
	popUpLayer.style.display = 'block';
	popUpHeader[0].innerHTML = '<p>메뉴 수정</p>'
	popUpContent[0].innerHTML=	 `
									<p>
										${name} <i class="fa-solid fa-caret-right"></i> <input type="text" id="newMenuName"/>
									</p>
									<div class="popup_btn">
										<button id="agree_btn" onclick=" updateMenu(${no})">확인</button>
									</div>
								`
}

/* *************** 메뉴 수정 **************** */
/* 분식 */
document.querySelectorAll('#snack-update-btn').forEach(button => {
    button.addEventListener('click', function() {
        const foodNo = this.getAttribute('foodNo');
		const foodName = this.getAttribute('foodName');
		const foodImage = this.getAttribute('foodImg');

		popupBox.classList.remove("menu-add-popupBox");
				popUpLayer.style.display = 'block';
				popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
				popUpContent[0].innerHTML= `
									<form action="/food/updateSnack" method="POST" enctype="multipart/form-data" name="menuUpdateFrm" id="menuAddFrm">
										<input type="hidden" name="menuNo" value="${foodNo}"/>
										<input type="hidden" name="existingImage" value="${foodImage}"/>

										<div class="menu-add-area">
											<div class="menu-name-area">
												<p>이름 :</p>
												<input type="text" name="menuName"  id="menuName" value="${foodName}"/>
											</div>
											<div class="image-area">
												<p>이미지 :</p>
												<div class="left-image-area">
													<label for="menuImage"> 
														<img class="preview"  src="${foodImage}">
													</label> 
													<i class="fa-solid fa-xmark" id="delete-image-btn"></i>
													<input type="file" class="inputImage" id="menuImage" accept="image/*" name="menuImage"> 
												</div>

												<div class="right-image-area">
<pre>
- 이미지 등록 
(권장사이즈 : 가로 200px X 세로 150px 혹은 4 : 3 비율)
- 파일양식 : jpg, jpeg, png(8MB 제한)
</pre>
													<div class="image-btn">
														<button type="button" id="image-add-btn">사진등록</button>
													</div>
												</div>
											</div>
										</div>

										<div class="popup_btn">
											<button type="submit" id="confirm_btn" class="menu_update">수정</button>
											<button id="cancel_btn" type="button" onclick="cancel()">취소</button>
										</div>
									</form>
								`
		popupBox.classList.add("menu-add-popupBox");
		
		const preview = document.getElementsByClassName("preview")[0];
		const inputImage = document.getElementsByClassName("inputImage")[0];


		// 미리보기 삭제(x버튼)
		document.getElementById("delete-image-btn").addEventListener("click", e=>{

			// 미리보기 이미지가 있을 경우
			if(preview.getAttribute("src") != ""){

				preview.removeAttribute("src");

				inputImage.value="";
				$('#delete-image-btn').css('display', 'none');
			}
		})

		// 파일이 선택되거나, 선택 후 취소되었을 때
		$('.inputImage').on("change", e=>{
			const file = e.target.files[0]; // 선택된 파일의 데이터

			if(file != undefined){  // 파일이 선택되었을 때
				const reader = new FileReader();    // 파일을 읽는 객체

				reader.readAsDataURL(file);
				// 지정된 파일을 읽은 후 result에 URL 형식으로 저장

				reader.onload= e=>{ // 파일을 다 읽은 후 수행
					preview.setAttribute("src", e.target.result);
				}

				$('#delete-image-btn').css('display', 'block');

			}else{  // 선택 후 취소되었을 때
				// -> 선택된 파일 없음 -> 미리보기 삭제
				preview.removeAttribute("src");
				$('#delete-image-btn').css('display', 'none');
			}
		})


		/* 사진 등록 버튼 */
		$('#image-add-btn').on("click", ()=>{
			inputImage.click();
		})

		$('#confirm_btn').on("click", (e)=>{
			if(document.getElementById("menuName").value == 0 ){
				e.preventDefault();
				popupBox.classList.remove("menu-add-popupBox");
				popUpContent[0].innerHTML = `
					<p>메뉴 이름을 기입해주세요!</p>
					<div class="popup_btn">
						<button id="menu-add-btn" onclick="agree()">확인</button>
					</div>
				`
			}
		})
    });
});


if(updateSnackMenu != ""){
	popupBox.classList.remove("menu-add-popupBox");
	popUpLayer.style.display = 'block';

	if(updateSnackMenu != 0){
		menuUpdateSuccess();
	}else{
		menuUpdateFail();
	}
}


function menuUpdateSuccess(){
	popupBox.classList.remove("menu-add-popupBox");

	popUpLayer.style.display = 'block';
	popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
	popUpContent[0].innerHTML= `
								<p>메뉴가 수정되었습니다!</p>
								<div class="popup_btn">
									<button id="agree_btn" onclick="agree()">확인</button>
								</div>
				`
}

function menuUpdateFail(){
	popupBox.classList.remove("menu-add-popupBox");
			popUpLayer.style.display = 'block';
			popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
			popUpContent[0].innerHTML= `
											<p>메뉴 수정에 실패하였습니다.</p>
											<div class="popup_btn">
												<button id="agree_btn" onclick="agree()">확인</button>
											</div>
										`
}

