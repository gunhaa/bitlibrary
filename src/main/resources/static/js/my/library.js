const params = new URL(location.href).searchParams;
const libNav = document.querySelectorAll(".nav-area > a");

for(let i = 0; i < libNav.length; i++){
    const subCategory = libNav[i].getAttribute("href").split("=")[1];

    if(params.get("sub") == subCategory){
        libNav[i].classList.add("selected");
    }
}

function bookCancel(rNo){
    fetch("/book",{
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : rNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("실패");
            return;
        }

        location.reload();
    })
    .catch(e=>console.log(e))
}

function reservCancel(rNo){
    fetch("/reserv",{
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : rNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("실패");
            return;
        }

        location.reload();
    })
    .catch(e=>console.log(e))
}

function classCancel(boardNo){
    fetch("/class",{
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : boardNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("실패");
            return;
        }

        location.reload();
    })
    .catch(e=>console.log(e))
}

function bookmarkCancel(bookNo){
    fetch("/bookmark",{
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : bookNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("실패");
            return;
        }

        location.reload();
    })
    .catch(e=>console.log(e))
}