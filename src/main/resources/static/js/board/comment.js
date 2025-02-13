// 댓글 등록
const commentContent = document.getElementById("commentContent")
const commentAdd = document.getElementById("commentAdd");

commentAdd.addEventListener("click", e=>{

    if(memberEmail == ""){
        alert("로그인 후 이용해주세요.");
        return;
    }

    if(commentContent.value.trim().length == 0){
        alert("댓글을 작성한 후 등록 버튼을 눌러주세요.");
        commentContent.value = "";
        commentContent.focus();
        return;
    }

    // insert
    const data = {"content" : commentContent.value,
                    "boardId" :  boardId};
    // post 방식                
    fetch("/user/"+ cateEngName + "/comment", {
        method   : "POST",
        headers  : {"Content-Type" : "application/json"},
        body     : JSON.stringify(data)
    }) 
    .then(resp => {
        if( !resp.ok ){
            throw new Error();
        }
    })
    .then(()=> {
        alert("댓글이 등록되었습니다.");
        commentContent.value = "";
        window.location.reload();
    })
    .catch(e => console.log("댓글 등록 중 오류 발생", e));

});


// 댓글 삭제
function deleteComment(commentId){
    if(confirm("정말 삭제하시겠습니까?")){
        fetch("/user/" + cateEngName + "/comment", {
            method  : "DELETE",
            headers : {"Content-Type" : "application/json"},
            body    : commentId
        })
        .then(resp => {
            if( resp.ok ){
                return resp.text();
            } else {
                throw new Error();
            }
        })
        .then(result => {
            if(result == "success"){
                alert("댓글이 삭제되었습니다.");
                window.location.reload();
            }else {
                alert("댓글 삭제에 실패했습니다.");
            }
        })
        .catch(e => console.log(e));
    }
}



// 댓글 수정 화면 전환
let beforeCommentRow;

function showUpdateComment(commentNo, btn){

    const temp = document.getElementsByClassName("update-textarea");

    if(temp.length > 0){

        if(confirm("다른 댓글이 수정 중입니다. 현재 댓글을 수정 하시겠습니까?")){

            temp[0].parentElement.innerHTML = beforeCommentRow;
            
        }else{
            return;
        }

    }
    const commentRow = btn.parentElement.parentElement; 

    beforeCommentRow = commentRow.innerHTML;

    let beforeContent = commentRow.children[1].innerHTML;

    commentRow.innerHTML = "";

    const textarea = document.createElement("textarea");
    textarea.classList.add("update-textarea");

    beforeContent =  beforeContent.replaceAll("&amp;", "&");
    beforeContent =  beforeContent.replaceAll("&lt;", "<");
    beforeContent =  beforeContent.replaceAll("&gt;", ">");
    beforeContent =  beforeContent.replaceAll("&quot;", "\"");
    
    textarea.value = beforeContent; // 내용 추가

    commentRow.append(textarea);


    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");
    

    const updateBtn = document.createElement("button");
    updateBtn.innerText = "수정";
    updateBtn.setAttribute("onclick", "updateComment(" + commentNo + ", this)");


    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "updateCancel(this)");


    commentBtnArea.append(updateBtn, cancelBtn);
    commentRow.append(commentBtnArea);
}


// 댓글 수정(AJAX)
function updateComment(commentNo, btn){

    const commentContent = btn.parentElement.previousElementSibling.value;
    
    const data = {"commentContent" : commentContent,
                  "commentNo" : commentNo}; 

    fetch("/user/" + cateEngName + "/comment", {
        method : "PUT",
        headers  :{"Content-Type" : "application/json"},
        body  : JSON.stringify(data)
    })
    .then(resp => resp.text())
    .then(result => {

        console.log(result)
        if(result > 0){
            alert("댓글이 수정되었습니다.");
            window.location.reload(); // 제발 되길
            // selectCommentList();

        }else{
            alert("댓글 수정 실패");
        }
    })
    .catch(e => console.log(e));

}


// 댓글 수정 취소
function updateCancel(btn){

    if(confirm("댓글 수정을 취소하시겠습니까?")){

        btn.parentElement.parentElement.innerHTML = beforeCommentRow;
    }
}




