const libNav = document.querySelectorAll(".nav-area > a");

for(let i = 1; i <= libNav.length; i++){
    if(params.get("m") == null){
        libNav[0].classList.add("selected");

    }else{
        if(params.get("m") == i){
            libNav[i - 1].classList.add("selected");
        }
    }
}

function bookcancel(rNo){
    fetch("/bookcancel",{
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

function reservcancel(rNo){
    fetch("/reservcancel",{
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

function classcancel(boardNo){
    fetch("/classcancel",{
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

function bookmarkcancel(bookNo){
    fetch("/bookmarkcancel",{
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