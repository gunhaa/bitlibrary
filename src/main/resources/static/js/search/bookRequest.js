

const requestBtn = document.getElementById("request-btn");
document.getElementById('currentDate').value= new Date().toISOString().slice(0, 10);

const popUpLayer = document.getElementById("popup_layer");
document.addEventListener("DOMContentLoaded", function () {
    const confirmBtn = document.getElementById("confirm_btn");

    confirmBtn.addEventListener("click", function () {
        popUpLayer.style.display = 'none'; // 모달 닫기
        location.href="/search/book-req/list";
    });

});

requestBtn.addEventListener("click", ()=>{
    const input = document.querySelectorAll(".request-form input");
    
    for(let i=0; i<input.length; i++){
        if(input[i].value.trim().length == 0){
            alert("입력하지 않은 항목이 있습니다.");
            return;
        }
    }

    const data = {
        isbn : input[0].value,
        bookTitle : input[2].value,
        bookAuthor : input[3].value,
        bookPublisher : input[4].value,
        bookPublicationDate : input[5].value,
        opinion : document.getElementById("opinion").value
    };

    fetch("/search/book-req/apply/v1", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp=>resp.json())
    .then(result=>{
        if(result.success === true){
            popUpLayer.style.display = "block";
        }

        if (result.success === false && result.message === "Book Request fail invalid memberId") {
            alert("로그인 상태가 아닙니다. 로그인을 하고 다시 시도해주세요.");
        } else if (result.success === false && result.message.startsWith("Book Request fail")) {
            alert("도서 신청에 오류가 발생했습니다.");
        }

        if(result.success === false && result.message==="Book already exist"){
            alert("이미 도서관에 있는 책입니다.")
        }
    })
    .catch(e=>console.log(e))
})
