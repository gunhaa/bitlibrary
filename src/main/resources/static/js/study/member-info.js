const btnBoard = document.getElementById('btnBoard');
const btnReply = document.getElementById('btnReply');

const pagination = document.getElementById('pagination');

if(btnBoard != null && btnReply != null) {
    btnBoard.addEventListener('click', () => handleButtonClick('Board', getBoards));
    btnReply.addEventListener('click', () => handleButtonClick('Reply', getReplies));
    window.onload = () => handleButtonClick('Board', getBoards);
}

function handleButtonClick(option, actionFunction) {
    const buttons = document.querySelectorAll('.nav-area > button');

    buttons.forEach(btn => btn.classList.remove('select'));
    document.getElementById(`btn${option}`).classList.add('select');

    actionFunction();
}

const getBoards = (currentPage = 0, pageSize = 10) => {
    pagination.innerHTML = '';
    
    fetchData(`/api/study/boards?page=${currentPage}&size=${pageSize}`, data => {
        updateTable(
            [
                { text: "번호" },
                { text: "제목" },
                { text: "작성일" }
            ],
            data.content.map(item => `<tr>
                <td>${item.id}</td>
                <td><a href="${item.link}">${item.title}</a></td>
                <td>${item.createdDate}</td>
            </tr>`),
            "작성한 게시글이 없습니다."
        );
        createPagination(data, getBoards);
    }, "게시글를 불러오는 데 실패했습니다.");
};

const getReplies = (currentPage = 0, pageSize = 10) => {
    pagination.innerHTML = '';
    
    fetchData(`/api/study/replies?page=${currentPage}&size=${pageSize}`, data => {
        updateTable(
            [
                { text: "번호" },
                { text: "내용" },
                { text: "작성일" }
            ],
            data.content.map(item => `<tr>
                <td>${item.id}</td>
                <td>${item.content}</td>
                <td>${item.createdDate}</td>
            </tr>`),
            "작성한 댓글이 없습니다"
        );
        createPagination(data, getReplies);
    }, "댓글를 불러오는 데 실패했습니다.");
};

const secessionFrm = document.getElementById("secessionFrm");
const agree = document.getElementById("agree");

if(secessionFrm != null){

    secessionFrm.addEventListener("submit", e=>{

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