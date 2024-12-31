
const requestBtn = document.getElementById("request-btn");
document.getElementById('currentDate').value= new Date().toISOString().slice(0, 10);

// 신청하기 버튼 클릭 시 모달을 여는 코드
const popUpLayer = document.getElementById("popup_layer");
document.addEventListener("DOMContentLoaded", function () {
    const confirmBtn = document.getElementById("confirm_btn");

    confirmBtn.addEventListener("click", function () {
        popUpLayer.style.display = 'none'; // 모달 닫기
        location.href="/book/2/1";

    });

    // 모달 외부 클릭 시 닫기
    /*window.addEventListener("click", function (e) {
        if (e.target === popUpLayer) {
            popUpLayer.style.display = 'none';
        }
    });*/
   
 
});

requestBtn.addEventListener("click", ()=>{
    const input = document.querySelectorAll(".request-form input");
    
    for(let i=0; i<input.length; i++){
        if(input[i].value.trim().length == 0){
            alert("입력하지 않은 항목이 있습니다.");
            return;
        }
    }

    requestBtn.setAttribute("disabled", "disabled");

    const obj = {
        reqTitle : input[0].value,
        memberNo : loginMemberNo,
        bookTitle : input[2].value,
        bookAuthor : input[3].value,
        bookPub : input[4].value,
        bookPubDate : input[5].value,
        reqOpinion : document.getElementById("opinion").value
    };

    fetch("/book/2/2", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp=>resp.text())
    .then(result=>{
        if(result > 0){
            popUpLayer.style.display = "block";
        }
    })
    .catch(e=>console.log(e))
})
