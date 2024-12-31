const memberName = document.getElementById("memberName");
const memberPhoneNo = document.getElementById("memberPhoneNo");
const updateFrm = document.getElementById("updateFrm");

if(updateFrm != null){

    initName = memberName.value;
    initPhoneNo = memberPhoneNo.value;

    memberName.addEventListener("input", ()=>{
        
        if(initName == memberName.value){
            memberName.removeAttribute("style");
            return;
        }
    
        const regExp = /^([가-힣]|\w){2,6}$/;
    
        if(regExp.test(memberName.value)){
            memberName.style.color = "green";
        }else { 
            memberName.style.color = "red";
        }
    
    })
    
    memberPhoneNo.addEventListener("input", ()=>{
    
        if(initPhoneNo == memberPhoneNo.value){
            memberPhoneNo.removeAttribute("style");
            return;
        }
    
        const regExp = /^0(10|2|[3-6][1-5]|70)\d{3,4}\d{4}$/;
    
        if(regExp.test(memberPhoneNo.value)){
            memberPhoneNo.style.color = "green";
        }else { 
            memberPhoneNo.style.color = "red";
        }
    
    })
    
    updateFrm.addEventListener("submit", e=>{
        if(memberName.style.color == "red"){
            alert("이름이 유효하지 않습니다.");
            memberName.focus();
            e.preventDefault();
            return;
        }
    
        if(memberPhoneNo.style.color == "red"){
            alert("전화번호가 유효하지 않습니다.");
            memberPhoneNo.focus();
            e.preventDefault();
            return;
        }
    })
}

const changePwFrm = document.getElementById("changePwFrm");
const currentPw = document.getElementById("currentPw");
const newPw = document.getElementById("newPw");
const newPwConfirm = document.getElementById("newPwConfirm");

if(changePwFrm != null){

    changePwFrm.addEventListener("submit", e=>{

        if(currentPw.value.trim().length == 0){
            alert("현재 비밀번호를 입력해주세요.");
            currentPw.value = "";
            currentPw.focus;
            e.preventDefault();
            return;
        }

        const regExp = /^(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()_=+,./?;:'"<>[\]{}\\|-])(?=.*[0-9]).{8,20}$/;

        if(!regExp.test(newPw.value)){
            alert("비밀번호가 유효하지 않습니다.");
            newPw.focus();
            e.preventDefault();
            return;
        }

        if(newPw.value != newPwConfirm.value){
            alert("비밀번호가 일치하지 않습니다.");
            newPwConfirm.focus();
            e.preventDefault();
            return;
        }

    })

}

const secessionFrm = document.getElementById("secessionFrm");
const memberPw = document.getElementById("memberPw");
const agree = document.getElementById("agree");

if(secessionFrm != null){

    secessionFrm.addEventListener("submit", e=>{

        if(memberPw.value.trim().length == 0){
            alert("비밀번호를 입력해주세요.");
            memberPw.focus();
            memberPw.value = "";
            e.preventDefault();
            return;
        }
        
        if(agree.value != "탈퇴하겠습니다"){
            alert("탈퇴문구를 입력해주세요.");
            agree.focus();
            e.preventDefault();
            return;
        }
    
        if(!confirm("정말로 탈퇴하시겠습니까?")){
            alert("회원 탈퇴 취소");
            e.preventDefault();
            return;
        }
    })

}