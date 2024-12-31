const checkObj = {
    "memberEmail" : false,
    "authKey" : false
}

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
        emailMessage.innerText = "유효한 이메일 형식입니다."
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");

        checkObj.memberEmail = true;
        
    }else {
        emailMessage.innerText = "유효하지 않은 이메일 형식입니다."
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");

        checkObj.memberEmail = false;
    }

})

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

        fetch("/sendEmail/resetPw?email="+memberEmail.value)
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
        alert("유효한 이메일을 작성해주세요.");
        memberEmail.focus();
    }

});

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

document.getElementById("resetPwFrm").addEventListener("submit", e=>{
    for(let key in checkObj){
        if(!checkObj[key]){
            let str = "";

            switch(key){
                case "memberEmail"  : str="이메일이"; break;
                case "authKey"      : str="인증번호가"; break;
            }

            alert(str + " 유효하지 않습니다.");

            document.getElementById(key).focus();

            e.preventDefault();
            return;
        }
    }
})