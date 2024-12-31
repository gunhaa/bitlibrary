// 유효성 검사 진행 여부 확인용 객체
const checkObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberName" : false,
    "memberGender" : false,
    "memberDateOfBirth" : false,
    "memberPhoneNo" : false,
    "authKey" : false
}


// 이메일 유효성 검사
const memberEmail = document.getElementById("memberEmail");
const emailMessage = document.getElementById("emailMessage");

memberEmail.addEventListener("input", () => {

    if(memberEmail.value.trim().length == 0) {
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";
        memberEmail.value = "";
        emailMessage.classList.remove("confirm");
        emailMessage.classList.remove("error");

        checkObj.memberEmail = false;
        return;
    }

    const regExp = /^[\w\-\_]{4,}@[a-z]+(\.\w+){1,3}$/;
    if(regExp.test(memberEmail.value)) {

        fetch("/dupCheck/email?email=" + memberEmail.value)
        .then(response => response.text())
        .then(result => {
            if(result == 0){
                emailMessage.innerText = "사용 가능한 이메일 입니다."
                emailMessage.classList.add("confirm");
                emailMessage.classList.remove("error");
                checkObj.memberEmail = true;
            }else{
                emailMessage.innerText = "중복된 이메일 입니다."
                emailMessage.classList.add("error");
                emailMessage.classList.remove("confirm");
                checkObj.memberEmail = false;
            }
        })
        .catch(e => console.log(e))

    }else {
        emailMessage.innerText = "유효하지 않은 이메일 형식입니다."
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");

        checkObj.memberEmail = false;
    }

})

// 비밀번호 유효성 검사
const memberPw = document.getElementById("memberPw");
const memberPwConfirm = document.getElementById("memberPwConfirm");
const pwMessage = document.getElementById("pwMessage");

memberPw.addEventListener("input", () => {

    if(memberPw.value.trim().length == 0) {
        pwMessage.innerText = "8~20자의 영문, 숫자, 특수문자를 조합하여 입력해주세요.";
        memberPw.value = "";
        pwMessage.classList.remove("confirm", "error");

        checkObj.memberPw = false;
        return;
    }

    const regExp = /^(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()_=+,./?;:'"<>[\]{}\\|-])(?=.*[0-9]).{8,20}$/;

    if(regExp.test(memberPw.value)) {
        checkObj.memberPw = true;

        if(memberPwConfirm.value.length==0){ 

            pwMessage.innerText = "유효한 형식입니다."
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");

        }else{
            checkPw();
        }

    }else {
        pwMessage.innerText = "유효하지 않은 비밀번호 형식입니다."
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");

        checkObj.memberPw = false;
    }

})

memberPwConfirm.addEventListener("input", function () {
    if(checkObj.memberPw) checkPw();
})

// 비밀번호 일치 검사
function checkPw() {
    
    if(memberPw.value == memberPwConfirm.value) {
        pwMessage.innerText="비밀번호가 일치합니다."
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");

        checkObj.memberPwConfirm = true;
    }else {
        pwMessage.innerText="비밀번호가 일치하지 않습니다."
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");

        checkObj.memberPwConfirm = false;
    }

}

// 이름 유효성 검사
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

// 생년월일 유효성 검사
const memberDateOfBirth = document.getElementById("memberDateOfBirth");
const dobMessage = document.getElementById("dobMessage");

memberDateOfBirth.addEventListener("input", () => {

    if(memberDateOfBirth.value.trim().length == 0) {
        dobMessage.innerText = "생년월일을 입력해주세요.";
        memberDateOfBirth.value = "";
        dobMessage.classList.remove("confirm", "error");

        checkObj.memberDateOfBirth = false;
        return;
    }

    const regExp = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;

    if(regExp.test(memberDateOfBirth.value)) {

        dobMessage.innerText = "유효한 형식입니다."
        dobMessage.classList.add("confirm");
        dobMessage.classList.remove("error");

        checkObj.memberDateOfBirth = true;

    }else {
        dobMessage.innerText = "유효하지 않은 생년월일 형식입니다."
        dobMessage.classList.add("error");
        dobMessage.classList.remove("confirm");

        checkObj.memberDateOfBirth = false;
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

// --------------------------- 이메일 인증 ---------------------------
const sendAuthKeyBtn = document.getElementById("sendAuthKeyBtn");
const authKeyMessage = document.getElementById("authKeyMessage");
let authTimer;
let authMin = 4;
let authSec = 59;

let tempEmail;

sendAuthKeyBtn.addEventListener("click", function(){
    authMin = 4;
    authSec = 59;
    checkObj.authKey = false;

    if(checkObj.memberEmail){

        fetch("/sendEmail/signUp?email="+memberEmail.value)
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                console.log("인증 번호가 발송되었습니다.")
                tempEmail = memberEmail.value;
            }else{
                console.log("인증번호 발송 실패")
            }
        })
        .catch(err => {
            console.log("이메일 발송 중 에러 발생");
            console.log(err);
        });

        alert("인증번호가 발송 되었습니다.");
       
        authKeyMessage.innerText = "05:00";
        authKeyMessage.classList.remove("confirm");

        clearInterval(authTimer);

        authTimer = window.setInterval(()=>{

            authKeyMessage.innerText = "0" + authMin + ":" + (authSec<10 ? "0" + authSec : authSec);
           
            if(authMin == 0 && authSec == 0){
                checkObj.authKey = false;
                clearInterval(authTimer);
                return;
            }

            if(authSec == 0){
                authSec = 60;
                authMin--;
            }

            authSec--;

        }, 1000)

    } else{
        alert("중복되지 않은 이메일을 작성해주세요.");
        memberEmail.focus();
    }

});




// 인증 확인
const authKey = document.getElementById("authKey");
const checkAuthKeyBtn = document.getElementById("checkAuthKeyBtn");


checkAuthKeyBtn.addEventListener("click", function(){

    if(!checkObj.authKey){

        if(authMin > 0 || authSec > 0){

            const obj = {"inputKey":authKey.value, "email":tempEmail}
            const query = new URLSearchParams(obj).toString()
            
            fetch("/sendEmail/checkAuthKey?" + query)
            .then(resp => resp.text())
            .then(result => {
                if(result > 0){
                    clearInterval(authTimer);
                    authKeyMessage.innerText = "인증되었습니다.";
                    authKeyMessage.classList.add("confirm");
                    checkObj.authKey = true;

                } else{
                    alert("인증번호가 일치하지 않습니다.")
                    checkObj.authKey = false;
                }
            })
            .catch(err => console.log(err));

        } else{
            alert("인증 시간이 만료되었습니다. 다시 시도해주세요.")
        }
    }

});

const memberGender = document.getElementsByName("memberGender");

// form 태그 제출 시 유효성 검사
document.getElementById("signUpFrm").addEventListener("submit", e=>{

    if(memberGender[0].checked || memberGender[1].checked){
        checkObj.memberGender = true;
    }

    for(let key in checkObj){
        if(!checkObj[key]){
            let str = "";

            switch(key){
                case "memberEmail"      : str="아이디(이메일)이"; break;
                case "memberPw"         : str="비밀번호가"; break;
                case "memberPwConfirm"  : str="비밀번호 확인이"; break;
                case "memberName"       : str="이름이"; break;
                case "memberGender"     : str="성별이"; break;
                case "memberDateOfBirth": str="생년월일이"; break;
                case "memberPhoneNo"    : str="전화번호가"; break;
                case "authKey"          : str="인증번호가"; break;
            }

            alert(str + " 유효하지 않습니다.");

            document.getElementById(key).focus();

            e.preventDefault();
            return;
        }
    }

})