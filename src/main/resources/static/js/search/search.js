const query = document.getElementById("query");
const queryResult = document.getElementById("queryResult");
const searchBtn = document.getElementById("searchBtn");
const searchDetailBtn = document.getElementById("index-btn");
const resetBtn = document.getElementById("refresh-btn");
const params = new URL(location.href).searchParams;
const options = document.querySelectorAll("#key > option");
let key;

const sidelow = document.querySelectorAll(".side-side-current a");
for(let i=0; i<sidelow.length; i++){
    if(sidelow[i].getAttribute("href") == location.pathname){
        sidelow[i].style.fontWeight = "bold";
    }
}

if(params.get("query") != null){
    search(params.get("query"), params.get("key"));
}

if(searchBtn != null){

    searchBtn.addEventListener("click", ()=>{
        
        if(query.value.trim().length != 0){
            
            for(let o of options){
                if(o.selected){
                    key = o.value;
                }
            }

            search(query.value, key);
        }
    })

}

function search(query, key){
    fetch("/book/search?query=" + query + 
        "&key=" + key + "&memberNo=" + loginMemberNo)
    .then(resp => resp.json())
    .then(result => {
        showBookList(result);
    })
    .catch(e => console.log(e))
}

if(searchDetailBtn != null){

    searchDetailBtn.addEventListener("click", ()=>{
        let flag = true;
        const inputDetail = document.getElementsByClassName("inputDetail");
        for(let i=0; i<inputDetail.length; i++){
            if(inputDetail[i].value.trim().length != 0){
                flag = false;
            }
        }
    
        if(flag){
            alert("검색어를 입력해주세요");
            return;
        }

        const options = document.querySelectorAll("#searchPrint > option");

        let limit;
        for(let o of options){
            if(o.selected){
                limit = o.value;
            }
        }

        fetch("/book/search?query=" + inputDetail[0].value + 
            "&author=" + inputDetail[1].value +
            "&pub=" + inputDetail[2].value +
            "&startYear=" + inputDetail[3].value +
            "&endYear=" + inputDetail[4].value +
            "&limit=" + limit +
            "&memberNo=" + loginMemberNo)
        .then(resp=>resp.json())
        .then(result=>{
            showBookList(result);
        })
        .catch(e=>console.log(e))

    })

}

function showBookList(result){

    const bList = result.bookList;
    const lList = result.likeList;

    queryResult.innerText = ""
    
    if(bList.length == 0){
        return;
    }

    for(let b of bList){
        const bookRow = document.createElement("div");
        bookRow.classList.add("book-row");

        const bookInfoWapper = document.createElement("div");
        bookInfoWapper.classList.add("book-info-wapper");

        const thumbnail = document.createElement("img");
        thumbnail.setAttribute("src", `${b.thumbnail}`);

        const bookInfo = document.createElement("div");
        bookInfo.classList.add("book-info");

        const span = document.createElement("span");
        if(lList.indexOf(b.bookNo) == -1){
            span.innerText = "☆";

        }else{
            span.innerText = "★";
        }

        span.setAttribute("onclick", `bookLike(this, ${b.bookNo}, "${loginMemberNo}")`);

        const p1 = document.createElement("p");
        p1.innerText = `${b.bookTitle}`;
        const p2 = document.createElement("p");
        p2.innerText = `${b.bookAuthor}`
        const p3 = document.createElement("p");
        p3.innerText = `${b.isbn}`;
        const p4 = document.createElement("p");
        p4.innerText = `책상태 : `;
        
        const borrowText = document.createElement("span");

        if(b.bookState == 'I' || b.returnDueDate != null){
            borrowText.style.color = "red";
            borrowText.innerText = "대출 불가능";
            p4.append(borrowText);

            if(b.returnDueDate != null){
                borrowText.innerText = "대출중";
                borrowText.style.color = "blue";
                const resvInfoBtn = document.createElement("button");
                resvInfoBtn.classList.add("resvInfoBtn");
                resvInfoBtn.innerText = `예약 / 소장정보`;
                resvInfoBtn.addEventListener("click", e=>{
                    const table = e.target.parentElement.parentElement.parentElement.nextElementSibling;
                    if(table.style.display == "none"){
                        table.style.display = "table";
                    }else{
                        table.style.display = "none";
                    }
                });
                const i = document.createElement("i");
                i.classList.add("fa-solid", "fa-caret-down");
                resvInfoBtn.append(i);

                p4.append(resvInfoBtn);
            }
            
        }else{
            borrowText.style.color = "green";
            borrowText.innerText = "대출 가능";
            p4.append(borrowText);
        }

        bookInfo.append(p1, p2, p3, p4);

        bookInfoWapper.append(thumbnail, bookInfo, span);

        bookRow.append(bookInfoWapper);

        queryResult.append(bookRow);

        if(b.bookState == 'I' || b.returnDueDate != null){
            const table = document.createElement("table");
            table.setAttribute("style", "display : none");
            
            const thead = document.createElement("thead");
            const tbody = document.createElement("tbody");

            const theadTr = document.createElement("tr");

            const th1 = document.createElement("th");
            th1.innerText = "도서관";
            const th2 = document.createElement("th");
            th2.innerText = "대출상태";
            const th3 = document.createElement("th");
            th3.innerText = "반납 예정일";
            const th4 = document.createElement("th");
            th4.innerText = "예약";
            const th5 = document.createElement("th");
            th5.innerText = "부록";
            const th6 = document.createElement("th");
            th6.innerText = "비고";

            const tbodyTr = document.createElement("tr");

            const td1 = document.createElement("td");
            td1.innerText = `JAVA LIBRARY`;
            const td2 = document.createElement("td");
            td2.innerText = `대출중
                                예약 가능
                                (예약 : ${b.resvCount}명)`;
            const td3 = document.createElement("td");
            td3.innerText = `${(b.returnDueDate)}`;
            const td4 = document.createElement("td");
            const td5 = document.createElement("td");
            td5.innerText = `${(b.bookSupple == 'N' ? "없음" : "있음")}`;
            const td6 = document.createElement("td");
            td6.innerText = ``;
            
            const resvBtn = document.createElement("button");
            resvBtn.innerText = "예약하기";
            resvBtn.classList.add("resvBtn")
            resvBtn.addEventListener("click", ()=>{showModal(b)});

            td4.append(resvBtn);

            theadTr.append(th1, th2, th3, th4, th5, th6);
            tbodyTr.append(td1, td2, td3, td4, td5, td6);

            thead.append(theadTr);
            tbody.append(tbodyTr);

            table.append(thead, tbody);

            bookRow.append(table);

        }
        
    }
}

const modal = document.getElementById("popup_layer");
function showModal(book){
    modal.style.display = "block";
    const img = document.querySelector(".popup_content_left > img");
    img.setAttribute("src", book.thumbnail);

    const detail = document.getElementsByClassName("popup_content_right")[0].children;
    detail[1].innerText = `도서명 : ${book.bookTitle}`;
    detail[2].innerText = `저자 : ${book.bookAuthor}`;
    detail[3].innerText = `발행처 : ${book.bookPub}`;
    detail[4].innerText = `발행연도 : ${book.bookPubDate}`;
    detail[5].innerText = `ISBN : ${book.isbn}`;

    const confirmBtn = document.getElementById("confirm_btn");
    confirmBtn.setAttribute("onclick", "addReservation(" + book.bookNo + ")");
}

const cancelBtn = document.getElementById("cancel_btn");
cancelBtn.addEventListener("click", ()=>{
    modal.style.display = "none";
})

function addReservation(bookNo){

    if(loginMemberNo == ""){
        alert("로그인 후 이용해주세요");
        modal.style.display = "none";
        return;
    }

    fetch("/book/resv?bookNo=" + bookNo + "&memberNo=" + loginMemberNo)
    .then(resp=>resp.text())
    .then(result=>{

        if(result == -1){
            alert("이미 예약한 도서입니다");
        }else if(result > 0){
            alert("예약 성공!!");
        }else{
            alert("예약 실패 ㅠㅠ");
        }

        modal.style.display = "none";

    })
    .catch(e=>console.log)
}

function bookLike(el, bookNo, memberNo){

    if(memberNo == ""){
        alert("로그인 후 이용해주세요.");
        return;
    }

    let check;
    if(el.innerText == "☆"){
        check = 0;
    }else{
        check = 1;
    }

    const obj = {
        bookNo : bookNo,
        memberNo : memberNo,
        check : check
    };

    fetch("/book/like", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            if(check == 0){
                el.innerText = "★";
            }else{
                el.innerText = "☆";
            }
        }
    })
    .catch(e=>console.log(e))

}

// swiper
/*const sampleSlider = new Swiper('.sample', {
    slidesPerView: 3,
    spaceBetween: 10,
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev"
    }
})*/

/* 초기화 버튼 */
if(resetBtn != null){

    resetBtn.addEventListener("click", ()=>{
        const inputDetail = document.getElementsByClassName("inputDetail");
        for(let i=0; i<inputDetail.length; i++){
            if(inputDetail[i].value.trim().length != 0){
                inputDetail[i].value="";
            }
        }
    
        let searchPrint = document.getElementById("searchPrint");
        if(searchPrint != 10){
            searchPrint=10;
            location.reload();
        }    
    
    })

}

