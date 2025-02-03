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

                if(result == "bookRequest delete success"){

                    if (document.referrer) {
                        window.location.href = document.referrer;
                    } else {
                        history.back();
                    }

                } else if (result == "not valid request"){
                    alert("삭제 중 문제가 발생하였습니다. 다시 시도해주세요");
                }

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

        const url = window.location.pathname;
        const isbn = url.split('/')[4];

        const data = {
            isbn : isbn,
            approval : "Y"
        };

        if(confirm("승인하시겠습니까?")){
            fetch("/search/book-req/approve/toggle/v1", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(data)
            })
            .then(resp => resp.text())
            .then(result => {
                console.log(result);
                if(result == "bookRequest status change success"){

                    if (document.referrer) {
                        window.location.href = document.referrer;
                    } else {
                        history.back();
                    }

                } else if (result == "not valid request"){
                    alert("삭제 중 문제가 발생하였습니다. 다시 시도해주세요");
                }

            })
            .catch(e => console.log(e))
        }
    })
}

const declinedBtn = document.getElementById("declinedBtn");
if(declinedBtn != null){
    declinedBtn.addEventListener("click", ()=>{

        const url = window.location.pathname;
        const isbn = url.split('/')[4];

        const data = {
            isbn : isbn,
            approval : "N"
        };

        if(confirm("거절하시겠습니까?")){
            fetch("/search/book-req/approve/toggle/v1", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(data)
            })
            .then(resp => resp.text())
            .then(result => {
                console.log(result);
                if(result == "bookRequest status change success"){

                    if (document.referrer) {
                        window.location.href = document.referrer;
                    } else {
                        history.back();
                    }

                } else if (result == "not valid request"){
                    alert("삭제 중 문제가 발생하였습니다. 다시 시도해주세요");
                }

            })
            .catch(e => console.log(e))
        }
    })
}
