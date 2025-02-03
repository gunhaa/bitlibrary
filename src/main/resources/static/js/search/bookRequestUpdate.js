const updateBtn = document.getElementById("updateBtn");


updateBtn.addEventListener("click", ()=>{

    const input = document.querySelectorAll(".request-form input");
    for(let i=0; i<input.length; i++){
        if(input[i].value.trim().length == 0){
            alert("입력하지 않은 항목이 있습니다.");
            return;
        }
    }

    const textarea = document.getElementById("opinion");
    if(textarea.value.trim().length == 0){
        alert("입력하지 않은 항목이 있습니다.");
        return;
    }


    const url = window.location.pathname;
    const prevIsbn = url.split('/')[4];

    const data = {
        prevIsbn : prevIsbn,
        email : document.querySelector("#requestEmail").innerText,
        isbn : input[0].value,
        bookTitle : input[2].value,
        bookAuthor : input[3].value,
        bookPublisher : input[4].value,
        bookPublicationDate : input[5].value,
        opinion : document.getElementById("opinion").value
    };

    fetch("/search/book-req/update/v1", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp=>resp.text())
    .then(result=>{

        if(result == "bookRequest update success"){

            const urlParams = new URLSearchParams(window.location.search);
            const page = urlParams.get("page") || 0;

            window.location.href = `/search/book-req/list?page=${page}`;

        } else if (result == "not valid request"){
            alert("수정 중 문제가 발생하였습니다. 다시 시도해주세요");
        } else if (result == "bookRequest isbn exist"){
            alert("이미 희망도서 신청이 되어 있는 책 입니다.");
        }

    })
    .catch(e=>console.log(e))
})