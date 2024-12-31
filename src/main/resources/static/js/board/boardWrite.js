const preview = document.getElementsByClassName("preview");
const inputImage = document.getElementsByClassName("inputImage");
const deleteImage = document.getElementsByClassName("delete-image");

const deleteSet = new Set();

for(let i=0; i<inputImage.length; i++){
    
    inputImage[i].addEventListener("change", e=>{
        
        const file = e.target.files[0];

        if(file != undefined){
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = e=>{
                preview[i].setAttribute("src", e.target.result);
                deleteSet.delete(i);
            }

        }else{
            preview[i].removeAttribute("src");
        }
    })

    deleteImage[i].addEventListener("click", ()=>{

        if(preview[i].getAttribute("src") != ""){
            preview[i].removeAttribute("src");
            inputImage[i].value = "";
        }
    })
}

// 게시글 작성 시 제목, 내용 작성 여부 검사
const boardWirteFrm = document.getElementById("boardWriteFrm");
const boardTitle = document.getElementsByName("boardTitle")[0];
const boardContent = document.querySelector("[name='boardContent']");

boardWirteFrm.addEventListener("submit", e=>{

    // 제목 미입력
    if(boardTitle.value.trim().length == 0){
        alert("제목을 입력해주세요.");
        boardTitle.focus();
        boardTitle.value = "";
        e.preventDefault();
        return;
    }

    // 내용 미입력
    if(boardContent.value.trim().length == 0){
        alert("내용을 입력해주세요.");
        boardContent.focus();
        boardContent.value = "";
        e.preventDefault();
        return;
    }

});