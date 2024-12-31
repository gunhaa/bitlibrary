const loadStudy = document.getElementById("seat")

//  로그인 상태를 확인한 후 좌석 예약 페이지로 이동
loadStudy.addEventListener("click", () => {
    if (loginMemberNo != "") {
        window.location.href = "/reservation/study"
    } else {
        alert("로그인 후 이용해 주세요.");
    }
    
});
