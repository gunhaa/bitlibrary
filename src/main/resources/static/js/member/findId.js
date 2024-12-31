const checkObj = {
    "memberName" : false,
    "memberPhoneNo" : false
}

const memberName = document.getElementById("memberName");
const nameMessage = document.getElementById("nameMessage");

memberName.addEventListener("input", () => {

    if(memberName.value.trim().length == 0) {
        nameMessage.innerText = "이름를 입력해주세요.";
        memberName.value = "";
        nameMessage.classList.remove("confirm", "error");

        checkObj.memberName = false;
        return;
    }

    const regExp = /^[가-힣]{2,6}$/;

    if(regExp.test(memberName.value)) {

        nameMessage.innerText = "유효한 형식입니다."
        nameMessage.classList.add("confirm");
        nameMessage.classList.remove("error");

        checkObj.memberName = true;

    }else {
        nameMessage.innerText = "유효하지 않은 이름 형식입니다."
        nameMessage.classList.add("error");
        nameMessage.classList.remove("confirm");

        checkObj.memberName = false;
    }

})

const memberPhoneNo = document.getElementById("memberPhoneNo");
const pnoMessage = document.getElementById("pnoMessage");

memberPhoneNo.addEventListener("input", () => {

    if(memberPhoneNo.value.trim().length == 0) {
        pnoMessage.innerText = "전화번호를 입력해주세요.(- 제외)";
        memberPhoneNo.value = "";
        pnoMessage.classList.remove("confirm", "error");

        checkObj.memberPhoneNo = false;
        return;
    }

    const regExp = /^0(10|2|[3-6][1-5]|70)\d{3,4}\d{4}$/;

    if(regExp.test(memberPhoneNo.value)) {

        pnoMessage.innerText = "유효한 형식입니다."
        pnoMessage.classList.add("confirm");
        pnoMessage.classList.remove("error");

        checkObj.memberPhoneNo = true;

    }else {
        pnoMessage.innerText = "유효하지 않은 전화번호 형식입니다."
        pnoMessage.classList.add("error");
        pnoMessage.classList.remove("confirm");

        checkObj.memberPhoneNo = false;
    }

})

document.getElementById("findIdFrm").addEventListener("submit", e=>{
    for(let key in checkObj){
        if(!checkObj[key]){
            let str = "";

            switch(key){
                case "memberName"       : str="이름이"; break;
                case "memberPhoneNo"        : str="전화번호가"; break;
            }

            alert(str + " 유효하지 않습니다.");

            document.getElementById(key).focus();

            e.preventDefault();
            return;
        }
    }
})