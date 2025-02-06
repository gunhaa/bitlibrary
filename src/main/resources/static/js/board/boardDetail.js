// 게시글 수정 버튼 클릭
const updateBtn = document.getElementById("updateBtn");

if(updateBtn != null){
    
    updateBtn.addEventListener("click", ()=>{
        location.href = "/user/" + cateEngName + '/update' + location.search;
    })
}

// 게시글 삭제 버튼 클릭
const deleteBtn = document.getElementById("deleteBtn");

if(deleteBtn != null){
    
    deleteBtn.addEventListener("click", ()=>{

        if(confirm("정말 삭제하시겠습니까?")){ 
            location.href = "/user/" + cateEngName + '/delete';
        }
    })
}

// 목록으로 버튼 클릭 시
const listBtn = document.getElementById("listBtn");

listBtn.addEventListener("click", ()=>{

    location.href = "/user/" + cateEngName + location.search;
})