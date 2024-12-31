const popUpLayer = document.getElementById("popup_layer");

const payBtn = document.getElementById("pay-btn");
const popUpHeader = document.getElementsByClassName("popup_header");
const popUpContent = document.getElementsByClassName("popup_content");
const popupBox = document.getElementsByClassName("popup_box")[0];


/* cost : 전체 총합 */
/* price : 음식 각각 가격 */
/* qty : 수량 */
let cost = 0;
let price = 0;
let qty = 1;

/* 요소 구분을 위한 인덱스번호 */
var i = 1;
let itemPrices = {};  // 각 아이템의 가격을 저장할 객체

if(payBtn!= null){

	/* 음료 구매 모달 */
	payBtn.addEventListener("click", ()=> {
	
		/* 비동기 형식 */
		fetch("/api/cafe")
		.then(resp => resp.json())
		.then(result => {
			let html = ``;
			result.forEach(coffee => {
				html += `<div class="mcafe-menu" onclick='addCartMenu("${coffee.foodName}"," ${coffee.foodPrice}", "${coffee.foodNo}")'>
							<img src="${coffee.foodImg}">
							<div class="mcafe-menu-detail">
								<h4>${coffee.foodName}</h4>
								<span>${coffee.foodDes}</span>
								<div class="mcafe-menu-price">
									<span>${coffee.foodPrice}원</span>
								</div>
							</div>
						</div>`
			})
	
			$('.mcafe-menu-list').html(html)
		})
		.catch( e => {
			console.log("예외 발생!")
			console.log(e)
		})
	
	
		popUpHeader[0].innerHTML= "<p>음료 구매</p>"
		popUpContent[0].innerHTML = `
										<div class="mcafe">
											<div class="mcafe-menu-area">
												<p>메뉴 :</p>
												<div class="mcafe-menu-list">
												</div>
												
											</div>
											<div class="mcafe-cart-area">
												<div class="mcafe-cart">
													<p>장바구니 :</p>
													<div class="mcafe-cart-list">
														<table class="mcafe-cart-table">
														</table>
													</div>
												</div>
												<div class="mcafe-price">
													<p>가격 :</p>
													<div id="price-result"></div>
												</div>
											</div>
										</div>
										<div class="popup_btn">
											<button id="confirm_btn">결제</button>
											<button id="cancel_btn">취소</button>
										</div>
									`
		const popupBox = document.getElementsByClassName("popup_box")[0];
		popupBox.classList.add("mcafe-popupBox");
		popUpLayer.style.display = 'block';
	
		/* 동적으로 생성된 버튼에 이벤트 리스너 추가 */
		const confirmBtn = document.getElementById("confirm_btn");
		const cancelBtn = document.getElementById("cancel_btn");
	
		/* 결제 버튼임 */
		/* 결제 버튼 눌렀을 때 결제창 api 시작해야됨 */
		confirmBtn.addEventListener("click", ()=>{
			IMP.init('imp03608625');	// 아임포트 관리자 콘솔에서 확인한 '가맹점 식별코드' 입력
			IMP.request_pay(
				{
					pg: "kakaopay.TC0ONETIME", 	//pg사명 or pg사명.CID (잘못 입력할 경우, 기본 PG사가 띄워짐)
					pay_method : "card",
					merchant_uid : "merchant_" + new Date().getTime(),	// 가맹점 주문번호(아임포트를 사용하는 가맹점에서 중복되지 않은 임의의 문자열을 입력)
					name:"카페", //결제창에 노출될 상품명
					amount : cost	// 금액
					/* 나중에 DB와 연결할 때 쓸 듯 */
					/*
					buyer_email : 'iamport@siot.do'//구매자 이메일,
					buyer_name : '구매자이름',
					buyer_tel : '010-1234-5678'구매자 연락처,
					buyer_addr : '서울특별시 강남구 삼성동' // 구매자 주소,
					buyer_postcode : '123-456' // 구매자 우편번호
					*/
				}, function(resp){	// callback
					var result = '';
					popupBox.classList.remove("mcafe-popupBox");
					
					if(resp.success){


						const data = {
							memberNo : loginMemberNo,
							foodNoList: Object.values(itemPrices).map(item => item.no),  
							foodQtyList: Object.values(itemPrices).map(item => item.qty) 
						}

						fetch("/cafe/pay",{
							method: "POST",
							headers: {"Content-Type" : "application/json"},
							body: JSON.stringify(data)
						})
						.then(resp=>resp.json())
						.then(res => {
							console.log("result : " +res)
							if(res>0){
								popUpHeader[0].innerHTML= "<p>음료 구매</p>"
								popUpContent[0].innerHTML = `<div class="pay-meal-ticket">
																<p>구매가 완료되었습니다.</p>
															</div>
															<div class="popup_btn">
																<button id="agree_btn" onclick="agree()">확인</button>
															</div>
															`
								result='0';
								popUpLayer.style.display = 'block';
								itemPrices={};
							}
							
						})
						.catch(e=> console.log(e))

						
						
	
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
						itemPrices={};
					}
	
					if(result == '0'){
						location.href="/myPayment";
					}
				}
			)
		})
	
		/* 캔슬 버튼 */
		cancelBtn.addEventListener("click", ()=> {
			qty = 0;
			price = 0;
			cost = 0;
			itemPrices = {};
			popUpLayer.style.display = 'none';
		})
	
	});
}



/* function - cancel() */
function cancelCartMenu(i){
	const mCartMenu = document.getElementById("mcart-menu" +i);
	mCartMenu.remove();
	delete itemPrices[i];  // 아이템 목록에서 제거
	updateTotalCost();  // 총합 다시 계산

}


/* function - addCartMenu() */
function addCartMenu(name, price, foodNo){
	const mCartTable = document.getElementsByClassName("mcafe-cart-table")[0];

	// foodNo를 기반으로 해당 메뉴가 이미 카트에 있는지 확인
	for(let i in itemPrices){
		if(itemPrices[i].no== foodNo){
			// 이미 있으면 qty만 증가시키기
			itemPrices[i].qty += 1;
			const qtyResult = document.getElementById("qty-result" + i);
			qtyResult.innerText = itemPrices[i].qty;

			// 총합 업데이트
			updateTotalCost();
			return;
		}
	}

	/* 해당 메뉴가 처음 추가될 때만 초기 수량 설정 */
	if (!itemPrices[i]) {
		itemPrices[i] = {price: Number(price), qty: 1 , no:foodNo};
	}

	mCartTable.innerHTML += `
								<tr class="mcafe-cart-menu" id="mcart-menu${i}">
									<td>
										<span>${name}</span>
										<input type="button" value='x' onclick='cancelCartMenu(${i})' id="cancelBtn"/>
									</td>
									<td class="meal-ticket-btnArea">
										<input type="button" onclick='count("minus", ${i})' value='-' class="minusBtn" id="minusBtn${i}"/>
										<div class="qty-result" id="qty-result${i}">1</div>
										<input type ="button" onclick='count("plus", ${i++})' value='+' id="plusBtn"/>
									</td>
								</tr>
							`;

		updateTotalCost();
	/* cost += Number(price);
	const priceResult = document.getElementById("price-result");
	priceResult.innerText = cost + "원"; */
}

/* count 함수  */
function count(type, i){
	const qtyResult = document.getElementById("qty-result"+i);
	let qty =  parseInt(qtyResult.innerText, 10);

	if(type === "plus"){
		qty += 1;

	}else if(type === "minus" && qty > 0){
		qty-=1;
	}

	// 수량 변경 적용
	itemPrices[i].qty = qty;
	qtyResult.innerText = qty;

	// 마이너스 버튼 block하기
	if (qty == 0) {
		document.getElementById("minusBtn"+i).style.backgroundColor='#ddd';
	} else {
		document.getElementById("minusBtn"+i).style.backgroundColor='#8FB788';
	}

	// 총합 업데이트
	updateTotalCost();
 	
}

/* 총합 업데이트 함수 */
function updateTotalCost() {
	cost = 0;  // 총합 초기화
	for (let key in itemPrices) {
		let item = itemPrices[key];
		cost += item.price * item.qty;
	}
	const priceResult = document.getElementById("price-result");
	cost = cost.toLocaleString('ko-KR');
	priceResult.innerText = cost + "원";
}


/* agree 함수 */
function agree(){
	if( price != 0 || cost!=0){
		qty = 0;
		price = 0;
		cost = 0;
		itemPrices = {};
		popUpLayer.style.display = 'none';
		window.location.href="/myPayment";
		
	}else{
		popUpLayer.style.display = 'none';
		window.location.reload();
	}
}

/* ***************** 관리자 ************************************** */

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
												<p style="margin:20px">정말 해당 메뉴를 삭제하시겠습니까?</p>
												<div class="popup_btn">
													<button id="agree_btn" onclick="agreeDel()">확인</button>
													<button id="cancel_btn" type="button" onclick="cancel()" style="margin:10px">취소</button>
												</div>
											`

				
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

/* 메뉴 추가 */
$('#menu-add-btn').on("click", ()=>{
	popupBox.classList.add("menu-add-popupBox");
	popUpLayer.style.display = 'block';
	popUpHeader[0].innerHTML= "<p>메뉴 추가</p>"
	popUpContent[0].innerHTML = `
								<form action="/cafe/insert" method="POST" enctype="multipart/form-data" name="menuAddFrm" id="menuAddFrm">
									<div class="menu-add-area">
										<div class="menu-name-price-area">
											<div class="menu-name-area">
												<p>이름 :</p>
												<input type="text" name="menuName"  id="menuName"/>
											</div>
											<div class="menu-price-area">
												<p>가격 :</p>
												<input type="text" name="menuPrice" id="menuPrice"/>
											</div>
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
									<div class="menu-desc-area">
										<p>설명 :</p>
										<textarea cols="30" rows="5" name="menuDes" id="menuDes"></textarea>
									</div>
									
									<div class="popup_btn">
										<button type="submit" id="confirm_btn" class="menu_add">추가</button>
										<button id="cancel_btn" type="button" onclick="cancel()">취소</button>
									</div>
								</form>
									
									`
	
									
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

		if(document.getElementById("menuPrice").value==0){
			e.preventDefault();
			popupBox.classList.remove("menu-add-popupBox");
			popUpContent[0].innerHTML = `
				<p>메뉴 가격을 기입해주세요!</p>
				<div class="popup_btn">
					<button id="menu-add-btn" onclick="agree()">확인</button>
				</div>
			`
		}

		if(isNaN(document.getElementById("menuPrice").value)){
			e.preventDefault();
			popupBox.classList.remove("menu-add-popupBox");
			popUpContent[0].innerHTML = `
				<p>메뉴 가격은 숫자를 입력하셔야합니다.</p>
				<div class="popup_btn">
					<button id="menu-add-btn" onclick="agree()">확인</button>
				</div>
			`
		}
	})
})

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

/* ***********************메뉴 수정*******************  */
document.querySelectorAll('#cafe-update-btn').forEach(button => {
    button.addEventListener('click', function() {
        const foodNo = this.getAttribute('foodNo');
		const foodName = this.getAttribute('foodName');
		const foodPrice = this.getAttribute('foodPrice');
		const foodImage = this.getAttribute('foodImg');
		const foodDes = this.getAttribute('foodDes');

		popupBox.classList.remove("menu-add-popupBox");
				popUpLayer.style.display = 'block';
				popUpHeader[0].innerHTML= "<p>메뉴 수정</p>"
				popUpContent[0].innerHTML= `
									<form action="/cafe/update" method="POST" enctype="multipart/form-data" name="menuAddFrm" id="menuAddFrm">
									<input type="hidden" name="menuNo" value="${foodNo}"/>
									<input type="hidden" name="existingImage" value="${foodImage}"/>
									<div class="menu-add-area">
										<div class="menu-name-price-area">
											<div class="menu-name-area">
												<p>이름 :</p>
												<input type="text" name="menuName"  id="menuName" value="${foodName}"/>
											</div>
											<div class="menu-price-area">
												<p>가격 :</p>
												<input type="text" name="menuPrice" id="menuPrice"  value="${foodPrice}"/>
											</div>
										</div>
										<div class="image-area">
											<p>이미지 :</p>
											<div class="left-image-area">
												<label for="menuImage"> 
													<img class="preview" src="${foodImage}">
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
									<div class="menu-desc-area">
										<p>설명 :</p>
										<textarea cols="30" rows="5" name="menuDes" id="menuDes">${foodDes}</textarea>
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

			if(document.getElementById("menuPrice").value==0){
				e.preventDefault();
				popupBox.classList.remove("menu-add-popupBox");
				popUpContent[0].innerHTML = `
					<p>메뉴 가격을 기입해주세요!</p>
					<div class="popup_btn">
						<button id="menu-add-btn" onclick="agree()">확인</button>
					</div>
				`
			}
		})
    });
});


if(updateMenu != ""){
	console.log("hi");
	popupBox.classList.remove("menu-add-popupBox");
	popUpLayer.style.display = 'block';

	if(updateMenu != 0){
		console.log("hi success");
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

