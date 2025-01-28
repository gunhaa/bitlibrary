const updateBtn = document.getElementById("updateBtn");

if(updateBtn != null){
    updateBtn.addEventListener("click",() => {
        location.href = location.pathname + "/update" + location.search;
    })
}

const deleteBtn = document.getElementById("deleteBtn");

if(deleteBtn != null){
    deleteBtn.addEventListener("click", () => {
        if(confirm("정말 삭제하시겠습니까?")){

            const url = window.location.pathname;
            const isbn = url.split('/')[4];

            const data = {
                isbn : isbn
            };

            fetch("/search/book-req/delete/v1", {
                method : "DELETE",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(data)
            })
            .then(resp => resp.text())
            .then(result => {
                console.log(result);
//                const currentUrl = new URL(window.location.href);
//
//                const path = currentUrl.pathname.split('/').pop();
//
//                currentUrl.pathname = path.join('/');
//
//                window.location.href = currentUrl.toString();
            })
            .catch(e => console.log(e))
        }
    })
}

const goToListBtn = document.getElementById("goToListBtn");

goToListBtn.addEventListener("click", ()=>{
    location.href = "/search/book-req/list" + location.search;
})

const approvalBtn = document.getElementById("approvalBtn");
if(approvalBtn != null){
    approvalBtn.addEventListener("click", ()=>{

        const obj = {
            requestNo : requestNo,
            approval : "Y"
        };

        if(confirm("승인하시겠습니까?")){
            fetch("/book2/admin/approve", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(obj)
            })
            .then(resp => resp.text())
            .then(result => {
                if(result > 0){
                    alert("도서 신청 승인 완료");
                    location.replace("/book/2/1" + location.search)
                }else{
                    alert("도서 신청 승인 실패 ㅠㅠ")
                }
            })
            .catch(e => console.log(e))
        }
    })
}

const declinedBtn = document.getElementById("declinedBtn");
if(declinedBtn != null){
    declinedBtn.addEventListener("click", ()=>{

        const obj = {
            requestNo : requestNo,
            approval : "N"
        };

        if(confirm("거절하시겠습니까?")){
            fetch("/book2/admin/approve", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(obj)
            })
            .then(resp => resp.text())
            .then(result => {
                if(result > 0){
                    if(result > 0){
                        alert("도서 신청 거절 완료");
                        location.replace("/book/2/1" + location.search)
                    }else{
                        alert("도서 신청 거절 실패 ㅠㅠ")
                    }
                }
            })
            .catch(e => console.log(e))
        }
    })
}
