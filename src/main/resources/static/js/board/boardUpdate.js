// 미리보기
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

            deleteSet.add(i);
        }
    })
}

// 게시글 수정 시 제목, 내용 작성 여부 검사
const boardWriteFrm = document.getElementById("boardWriteFrm");
const boardTitle = document.getElementsByName("boardTitle")[0];
const boardContent = document.querySelector("[name='boardContent']");

boardWriteFrm.addEventListener("submit", e=>{

    if(boardTitle.value.trim().length == 0){
        alert("제목을 입력해주세요.");
        boardTitle.focus();
        boardTitle.value = "";
        e.preventDefault();
        return;
    }

    if(boardContent.value.trim().length == 0){
        alert("내용을 입력해주세요.");
        boardContent.focus();
        boardContent.value = "";
        e.preventDefault();
        return;
    }

    document.querySelector("[name='deleteList']").value = Array.from(deleteSet);
})

const li = document.querySelectorAll(".side-menu > li");

switch(cateCode){
    case "15" : li[0].classList.add("side-current"); break;
    case "16" : li[1].classList.add("side-current"); break;
    case "17" : li[2].classList.add("side-current"); break;
    case "18" : li[3].classList.add("side-current"); break;
}
