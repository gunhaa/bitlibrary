// 댓글 목록 조회
function selectCommentList(){

    // Create : 생성, 삽입 (POST)
    // Read   : 조회 (GET)
    // Update : 수정 (PUT, PATCH)
    // Delete : 삭제 (DELETE)

    // get 방식
    fetch("/comment?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(list =>{
        // console.log(list);

        const commentList = document.getElementById("commentList");
        commentList.innerHTML = "";
        
        for(let comment of list){

            // 행
            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");
            
            // 작성자 영역
            const commentWriter = document.createElement("p");
            commentWriter.classList.add("comment-writer");

            // 작성자 이름
            const memberName = document.createElement("span");
            memberName.innerText = comment.memberName;

            // 작성일
            const commentDate = document.createElement("span");
            commentDate.classList.add("comment-date");
            commentDate.innerText = "(" + comment.commentCreateDate + ")";

            // 작성자 영역에 이름, 작성일 추가
            commentWriter.append(memberName, commentDate);

            // 댓글 내용
            const commentContent = document.createElement("p");
            commentContent.classList.add("comment-content");
            commentContent.innerHTML = comment.commentContent;

            // 행에 작성자, 댓글 내용 추가
            commentRow.append(commentWriter, commentContent);

            
            // 로그인이 되어 있다면
            if(loginMemberNo != ""){
                
                // 댓글 수정/삭제 버튼 영역 
                const commentBtnArea = document.createElement("div");
                commentBtnArea.classList.add("comment-btn-area");

                // 로그인한 회원 번호 == 댓글 작성한 회원 번호
                if(loginMemberNo == comment.memberNo){

                    // 댓글 수정 버튼
                    const updateBtn = document.createElement("button");
                    updateBtn.innerText = "수정";
                    updateBtn.setAttribute("onclick", "showUpdateComment(" + comment.commentNo + ", this)");

                    // 댓글 삭제 버튼
                    const deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "삭제";
                    deleteBtn.setAttribute("onclick", "deleteComment(" + comment.commentNo + ")");

                    // 버튼 영역에 수정/삭제 버튼 추가
                    commentBtnArea.append(updateBtn, deleteBtn);
                     
                }

                commentRow.appen(commentBtnArea);
            }

            commentList.append(commentRow);
        
        }

    })
    // .catch(e => console.log(e));
    .catch(e => console.log(e));
}


// 댓글 등록
const commentContent = document.getElementById("commentContent")
const commentAdd = document.getElementById("commentAdd");

commentAdd.addEventListener("click", e=>{

    if(loginMemberNo == ""){
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
    const data = {"commentContent" : commentContent.value,
                    "memberNo" : loginMemberNo,
                    "boardNo" :  boardNo};
    // post 방식                
    fetch("/comment", {
        method   : "POST",
        headers  : {"Content-Type" : "application/json"},
        body     : JSON.stringify(data)
    }) 
    .then(resp => resp.text())
    .then(result => {
        console.log(result)
        if(result > 0){
            alert("댓글이 등록되었습니다.");
            commentContent.value = "";
            window.location.reload();

        }else{
            alert("댓글 등록에 실패했습니다. 다시 작성해주세요.");
        }
    })
    .catch(e => console.log("댓글 등록 중 오류 발생", e));

});


// 댓글 삭제
function deleteComment(commentNo){

    if(confirm("정말 삭제하시겠습니까?")){

        fetch("/comment", {
            method  : "DELETE",
            headers : {"Content-Type" : "application/json"},
            body    : commentNo
        })
        .then(resp => resp.text())
        .then(result => {

            if(result  > 0){
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

    fetch("/comment", {
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




