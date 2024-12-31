// 게시글 수정 버튼 클릭 시
const updateBtn = document.getElementById("updateBtn");

if(updateBtn != null){

    updateBtn.addEventListener("click",()=>{
        location.href = location.pathname + "/update" + location.search
    })
    
}


// 모달창 삭제 확인 버튼 클릭 시
const deleteConfirmBtn = document.getElementById("delete_confirm_btn");

if(deleteConfirmBtn != null){
    deleteConfirmBtn.addEventListener("click", ()=>{
        location.href = location.pathname + "/delete" + location.search
    })
}

// 목록으로
const goToListBtn = document.getElementById("goToListBtn")

if (goToListBtn != null) {

    goToListBtn.addEventListener("click",()=>{
    
        location.href = "/scheduling/" + cateCode + location.search;
    })

}



/* 알림창 모달 */
const popupLayer = document.getElementById("popup_layer");
const cancelPopup = document.getElementById("cancel_btn");


/* 게시글 삭제 모달 열기 */
function deleteModal(){
    popupLayer.style.display = "block";
}
/* 게시글 등록 취소 모달 열기 */
function cancelModal(){
    popupLayer.style.display = "block";
}

/* 게시글 등록 모달 열기 */
function registModal(){
    popupLayer.style.display = "block";
}

/* 게시글 등록 모달 열기 */
function reservSeatModal(){
    popupLayer.style.display = "block";
}

/* 알림창 모달 닫기 */
cancelPopup.addEventListener("click",() => {
    popupLayer.style.display = "none";
});


 /* 신청 버튼 클릭 시 */
// 클래스 신청 함수
function applyModal() {
    const applyUrl = `/scheduling/12/${boardNo}/apply`; // URL 구성

    // 확인 모달을 표시
    document.getElementById('popup_layer').style.display = 'block';

    // 확인 버튼 클릭 시 서버에 POST 요청
    document.getElementById('confirm_btn')?.addEventListener('click', () => {
        fetch(applyUrl, {
            method: "POST", // POST 메서드 사용
            headers: {
                "Content-Type": "application/json" // JSON 데이터로 설정
            },
            body: JSON.stringify({ cp: cp }) // JSON 본문 추가
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                window.location.href = `/myLibrary/reserv?m=3`; // 성공 시 리다이렉트
            } else if (data.status === "error") {
                // 신청 실패 모달 표시
                document.getElementById('applyModal').style.display = 'block';
                document.querySelector('#applyModal .close_btn').addEventListener('click', () => {
                    document.getElementById('applyModal').style.display = 'none'; // 모달 닫기
                });
            }
        })
        .catch(error => console.log('Error:', error));

        // 모달을 닫기
        document.getElementById('popup_layer').style.display = 'none';
    });

    // 취소 버튼 클릭 시 모달을 닫기
    document.getElementById('cancel_btn')?.addEventListener('click', () => {
        document.getElementById('popup_layer').style.display = 'none'; // 모달 닫기
    });
}




