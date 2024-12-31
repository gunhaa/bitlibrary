const loadSeminar = document.getElementById("seminar")

//  로그인 상태를 확인한 후 세미나실 예약 페이지로 이동
loadSeminar.addEventListener("click", () => {
    if (loginMemberNo != "") {
        window.location.href = "/reservation/seminar"
    } else {
        alert("로그인 후 이용해 주세요.");
    }
    
});
