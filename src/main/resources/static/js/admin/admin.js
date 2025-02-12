// 삭제여부 업데이트할 이메일, 게시글, 댓글을 저장하는 Set
const updateSet = new Set();

// 체크박스를 선택할 때마다 호출되는 함수
const toggleItem = e => {
    const selectItem = e.target.nextElementSibling.value;
    if (e.target.checked) {
        updateSet.add(selectItem);
    } else {
        updateSet.delete(selectItem);
    }
};

// 삭제 여부 변경을 처리하는 함수
const handleSubmit = (url, getData) => {
    if (updateSet.size === 0) {
        alert("선택 후 눌러주세요");
        return;
    }

    fetch(url, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(Array.from(updateSet))
    })
    .then(resp => {
        if (!resp.ok) {
            alert("변경 실패ㅠㅠ");
            throw new Error(resp.status);
        }
    })
    .then(() => {
        alert('변경 성공!');
        getData();
    })
    .catch(error => {
        console.error(error);
        alert('서버와 연결하는 데 실패했습니다. 네트워크를 확인해주세요.');
    });
};

// 회원 정보를 불러오는 함수
const getAllMembers = (currentPage = 0, pageSize = 10) => {
    pagination.innerHTML = '';

    fetchData(`/api/admin/members?page=${currentPage}&size=${pageSize}`, data => {
        updateTable(
            [
                { text: "탈퇴여부 변경" },
                { text: "이름" },
                { text: "가입일" },
                { text: "탈퇴여부" }
            ],
            data.content.map(item => `<tr>
                <td>
                    <input type="checkbox" onchange="toggleItem(event)">
                    <input type="hidden" value="${item.email}">
                </td>
                <td>${item.name}</td>
                <td>${item.createdDate}</td>
                <td>${item.deleted ? 'Y' : 'N'}</td>
            </tr>`),
            "회원정보가 없습니다."
        );
        createPagination(data, getAllMembers);
    }, "회원 정보를 불러오는 데 실패했습니다.");
};

// 게시글을 불러오는 함수
const getAllBoards = (currentPage = 0, pageSize = 10) => {
    pagination.innerHTML = '';

    fetchData(`/api/admin/boards?page=${currentPage}&size=${pageSize}`, data => {
        updateTable(
            [
                { text: "삭제여부 변경", style: "width: 130px;" },
                { text: "제목" },
                { text: "작성자", style: "width: 120px;" },
                { text: "작성일", style: "width: 120px;" },
                { text: "삭제여부", style: "width: 100px;" }
            ],
            data.content.map(item => `<tr>
                <td>
                    <input type="checkbox" onchange="toggleItem(event)">
                    <input type="hidden" value="${item.id}">
                </td>
                <td><a href="${item.link}">${item.title}</a></td>
                <td>${item.name}</td>
                <td>${item.createdDate}</td>
                <td>${item.deleted ? 'Y' : 'N'}</td>
            </tr>`),
            "작성된 게시글이 없습니다."
        );
        createPagination(data, getAllBoards);
    }, "게시글를 불러오는 데 실패했습니다.");
};

// 댓글을 불러오는 함수
const getAllReplies = (currentPage = 0, pageSize = 10) => {
    pagination.innerHTML = '';

    fetchData(`/api/admin/replies?page=${currentPage}&size=${pageSize}`, data => {
        updateTable(
            [
                { text: "삭제여부 변경", style: "width: 130px;" },
                { text: "내용" },
                { text: "작성자", style: "width: 120px;" },
                { text: "작성일", style: "width: 120px;" },
                { text: "삭제여부", style: "width: 100px;" }
            ],
            data.content.map(item => `<tr>
                <td>
                    <input type="checkbox" onchange="toggleItem(event)">
                    <input type="hidden" value="${item.id}">
                </td>
                <td>${item.content}</td>
                <td>${item.name}</td>
                <td>${item.createdDate}</td>
                <td>${item.deleted ? 'Y' : 'N'}</td>
            </tr>`),
            "작성된 댓글이 없습니다."
        );
        createPagination(data, getAllReplies);
    }, "댓글를 불러오는 데 실패했습니다.");
};

// 페이지 상단의 사이드바 메뉴 활성화 처리
const side = document.querySelectorAll(".side-wrap > a");
const params = new URL(location.href).searchParams;

for (let i = 0; i < side.length; i++) {
    if (side[i].getAttribute("href") == location.pathname) {
        side[i].style.fontWeight = "bold";
    }
}

// 전체 선택 체크박스를 클릭했을 때 처리하는 이벤트
const selectAllCheckbox = document.getElementById("selectAll");
const deleteStatusCheckBox = document.querySelectorAll("td > input[type='checkbox']");

if (selectAllCheckbox != null) {
    selectAllCheckbox.addEventListener("click", () => {
        for (let i = 0; i < deleteStatusCheckBox.length; i++) {
            const selectItem = deleteStatusCheckBox[i].nextElementSibling.value;
            if (selectAllCheckbox.checked) {
                updateSet.add(selectItem);
            } else {
                updateSet.delete(selectItem);
            }
            deleteStatusCheckBox[i].checked = selectAllCheckbox.checked;
        }
    });
}

// 버튼 이벤트 리스너 등록
const buttons = [
    { id: "member-submit", url: "/api/admin/member", getData: getAllMembers },
    { id: "board-submit", url: "/api/admin/board", getData: getAllBoards },
    { id: "reply-submit", url: "/api/admin/reply", getData: getAllReplies }
];

// 페이지가 완전히 로드된 후 버튼 처리
document.addEventListener("DOMContentLoaded", () => {
    buttons.forEach(button => {
        const submitBtn = document.getElementById(button.id);
        if (submitBtn != null) {
            // 버튼이 존재하면, 해당 버튼의 'getData' 함수를 실행
            button.getData();
            submitBtn.addEventListener("click", () => handleSubmit(button.url, button.getData));
        }
    });
});
