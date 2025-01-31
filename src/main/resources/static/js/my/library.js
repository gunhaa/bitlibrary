const btnCurrentlyBorrowedBooks = document.getElementById('btnCurrentlyBorrowedBooks');
const btnBookLoanHistory = document.getElementById('btnBookLoanHistory');
const btnBookReservations = document.getElementById('btnBookReservations');
const btnBookApplications = document.getElementById('btnBookApplications');

if (btnCurrentlyBorrowedBooks || btnBookLoanHistory || btnBookReservations || btnBookApplications) {
    btnCurrentlyBorrowedBooks.addEventListener('click', () => handleButtonClick('CurrentlyBorrowedBooks', getCurrentlyBorrowedBooks));
    btnBookLoanHistory.addEventListener('click', () => handleButtonClick('BookLoanHistory', getBookLoanHistory));
    btnBookReservations.addEventListener('click', () => handleButtonClick('BookReservations', getBookReservations));
    btnBookApplications.addEventListener('click', () => handleButtonClick('BookApplications', getBookApplications));
}

const today = new Date().toISOString().split('T')[0];

// 버튼 클릭 처리 공통 함수
function handleButtonClick(option, actionFunction) {
    const buttons = document.querySelectorAll('.nav-area > button');

    buttons.forEach(btn => btn.classList.remove('select'));
    document.getElementById(`btn${option}`).classList.add('select');

    actionFunction();
}

const fetchData = (url, onSuccess, errorMessage) => {
    fetch(url, { method: "GET", headers: { "Content-Type": "application/json" } })
    .then(response => {
        if (response.ok) return response.json();
        alert(errorMessage + ' 상태 코드: ' + response.status);
        throw new Error('Failed to fetch');
    })
    .then(onSuccess)
    .catch(error => {
        console.error('Error:', error);
        alert('서버와 연결하는 데 실패했습니다. 네트워크를 확인해주세요.');
    });
};

const updateTable = (headers, rows, emptyMessage) => {
    const tableHead = document.querySelector('#list-table > thead');
    const tableBody = document.querySelector('#list-table > tbody');
    tableHead.innerHTML = `<tr>${headers.map(h => `<th${h.style ? ` style=\"${h.style}\"` : ''}>${h.text}</th>`).join('')}</tr>`;
    tableBody.innerHTML = rows.length ? rows.join('') : `<tr><th style="height: 100px;" colspan="${headers.length}">${emptyMessage}</th></tr>`;
};

const getCurrentlyBorrowedBooks = () => {
    document.getElementById('pagination').innerHTML = '';

    fetchData("/api/study/books/loans/current", data => {
        updateTable(
            [
                { text: "도서명" },
                { text: "대출일/반납예정일", style: "width: 150px;" },
                { text: "대출상태", style: "width: 100px;" }
            ],
            data.map(item => `<tr>
                <td>${item.title}</td>
                <td>${item.borrowDate}<br>${item.returnDueDate}</td>
                <td>${currentDate <= item.returnDueDate ? '대출중' : '<span style="color:red;">연체중</span>'}</td>
            </tr>`),
            "대출중인 도서가 없습니다"
        );
    }, "대출 중인 도서를 불러오는 데 실패했습니다.");
};

const getBookLoanHistory = () => {
    document.getElementById('pagination').innerHTML = '';

    fetchData("/api/study/books/loans/history", data => {
        updateTable(
            [
                { text: "도서명" },
                { text: "대출일/반납예정일", style: "width: 150px;" },
                { text: "반납일/반납상태", style: "width: 150px;" }
            ],
            data.content.map(item => `<tr>
                <td>${item.title}</td>
                <td>${item.borrowDate}<br>${item.returnDueDate}</td>
                <td>${item.returnDate}<br>${item.returnDate <= item.returnDueDate ? '정상반납' : '<span style="color:red;">연체</span>'}</td>
            </tr>`),
            "이전 대출내역이 없습니다"
        );
        createPagination(data, getBookLoanHistory);
    }, "이전 대출내역를 불러오는 데 실패했습니다.");
};

const getBookReservations = () => {
    document.getElementById('pagination').innerHTML = '';

    fetchData("/api/study/books/reservation", data => {
        updateTable(
            [
                { text: "예약번호", style: "width: 100px;" },
                { text: "도서명" },
                { text: "신청일자", style: "width: 120px;" },
                { text: "예약만료일", style: "width: 120px;" },
                { text: "예약취소", style: "width: 100px;" }
            ],
            data.map(item => `<tr>
                <td>${item.id}</td>
                <td>${item.title}</td>
                <td>${item.reservationDate}</td>
                <td>${item.reservationEndDate}</td>
                <td><button onclick="cancelBookReservation(${item.id})">취소</button></td>
            </tr>`),
            "예약 내역이 없습니다"
        );
    }, "예약내역를 불러오는 데 실패했습니다.");
};

const getBookApplications = (currentPage = 0, pageSize = 10) => {
    document.getElementById('pagination').innerHTML = '';
    
    fetchData(`/api/study/books/application?page=${currentPage}&size=${pageSize}`, data => {
        updateTable(
            [
                { text: "신청번호", style: "width: 100px;" },
                { text: "신청 도서명" },
                { text: "저자" },
                { text: "출판사" },
                { text: "신청상태", style: "width: 100px;" }
            ],
            data.content.map(item => `<tr>
                <td>${item.id}</td>
                <td>${item.title}</td>
                <td>${item.author}</td>
                <td>${item.publisher}</td>
                <td>${item.approve === 'W' ? '대기' : item.approve === 'Y' ? '승인' : '거절'}</td>
            </tr>`),
            "신청 내역이 없습니다"
        );
        createPagination(data, getBookApplications);
    }, "신청내역를 불러오는 데 실패했습니다.");
};


function createPagination(pageData, getDataFunction) {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';
    const pageRange = 5;
    const startPage = Math.floor(pageData.number / pageRange) * pageRange;
    const endPage = Math.min(startPage + pageRange - 1, pageData.totalPages - 1);
    const prevPage = startPage > 0 ? startPage - 1 : 0;
    const nextPage = endPage < pageData.totalPages - 1 ? endPage + 1 : pageData.totalPages - 1;

    paginationContainer.append(
        createPageLink('<<', 0, getDataFunction),
        createPageLink('<', prevPage, getDataFunction)
    );

    for (let i = startPage; i <= endPage; i++) {
        paginationContainer.append(i === pageData.number ? createCurrentPage(i) : createPageLink(i + 1, i, getDataFunction));
    }

    paginationContainer.append(
        createPageLink('>', nextPage, getDataFunction),
        createPageLink('>>', pageData.totalPages - 1, getDataFunction)
    );
}

function createPageLink(text, page, getDataFunction) {
    const li = document.createElement('li');
    const a = document.createElement('a');
    a.textContent = text;
    a.addEventListener('click', () => getDataFunction(page));
    li.append(a);
    return li;
}

function createCurrentPage(page) {
    const li = document.createElement('li');
    const a = document.createElement('a');
    a.classList.add('current');
    a.textContent = page + 1;
    li.append(a);
    return li;
}

function cancelHttpRequest(apiUrl, data) {
    fetch(apiUrl, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(resp => {
        if (!resp.ok) {
            alert("실패");
        }
    })
    .catch(e => console.log(e));
}

// 각 취소 처리 함수
const cancelBookReservation = reservNo => cancelHttpRequest("/api/study/reservation/book", { id: reservNo });
const cancelSeatReservation = reservNo => cancelHttpRequest("/api/study", { id: reservNo });
const cancelClassApplicant = boardNo => cancelHttpRequest("/api/study", { id: boardNo });
const cancelBookLike = bookNo => cancelHttpRequest("/api/study", { id: bookNo });

window.onload = () => handleButtonClick('CurrentlyBorrowedBooks', getCurrentlyBorrowedBooks);
