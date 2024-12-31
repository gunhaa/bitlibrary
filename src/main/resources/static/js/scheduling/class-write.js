const imgMent = document.getElementById("img-ment");
const deleteImage = document.getElementById("delete-image");
const preview = document.getElementById("preview");
const inputImage = document.getElementById("img0")

inputImage.addEventListener("change",e=>{
    const file = e.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.setAttribute("src", e.target.result );
            preview.style.display = "block"; // 이미지 표시
            imgMent.innerText=""; // 기본 텍스트 숨기기
        };
        reader.readAsDataURL(file);
    } else {
        // 파일이 선택되지 않았을 때
        preview.style.display = 'none'; // 이미지 숨김
        imgMent.innerText='이미지 파일을 선택해 주세요.';
    }
});

deleteImage.addEventListener("click", ()=>{
    if(preview.getAttribute("src") != ""){
        preview.removeAttribute("src");
        inputImage.value="";
        imgMent.innerText = "이미지 파일을 선택해 주세요.";
    }
})

// 제목, 교육기간(시작~끝), 모집기간(시작~끝), 최대인원 작성 여부 검사
const boardWriteFrm = document.getElementById("boardWriteFrm");
const boardTitle = document.getElementById("boardTitle");
const startDt = document.getElementById("startDt");
const endDt = document.getElementById("endDt");
const recStartDt = document.getElementById("recStartDt");
const recEndDt = document.getElementById("recEndDt");
const maxP = document.getElementById("maxP");

boardWriteFrm.addEventListener("submit", e=>{
    if(boardTitle.value.trim().length == 0){
        alert("제목을 입력해주세요");
        boardTitle.focus();
        boardTitle.value="";
        e.preventDefault();
        return;
    }

    if(startDt.value==""){
        alert("교육 시작 기간을 입력해주세요.")
        startDt.focus();
        startDt.value="";
        e.preventDefault();
        return;
    }

    if(endDt.value==""){
        alert("교육 끝 기간을 입력해주세요.")
        endDt.focus();
        endDt.value="";
        e.preventDefault();
        return;
    }

    if(recStartDt.value==""){
        alert("신청 시작 기간을 입력해주세요.")
        recStartDt.focus();
        recStartDt.value="";
        e.preventDefault();
        return;
    }

    if(recEndDt.value==""){
        alert("신청 끝 기간을 입력해주세요.")
        recEndDt.focus();
        recEndDt.value="";
        e.preventDefault();
        return;
    }

    if(maxP.value==""){
        alert("최대인원을 입력해주세요.")
        maxP.focus();
        maxP.value="";
        e.preventDefault();
        return;
    }

})