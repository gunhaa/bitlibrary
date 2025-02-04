const btnCurrentlyBorrowedBooks = document.getElementById('btnCurrentlyBorrowedBooks');
const btnBookLoanHistory = document.getElementById('btnBookLoanHistory');
const btnBookReservations = document.getElementById('btnBookReservations');
const btnBookApplications = document.getElementById('btnBookApplications');

const pagination = document.getElementById('pagination');

btnCurrentlyBorrowedBooks.addEventListener('click', () => handleButtonClick('CurrentlyBorrowedBooks', getCurrentlyBorrowedBooks));
btnBookLoanHistory.addEventListener('click', () => handleButtonClick('BookLoanHistory', getBookLoanHistory));
btnBookReservations.addEventListener('click', () => handleButtonClick('BookReservations', getBookReservations));
btnBookApplications.addEventListener('click', () => handleButtonClick('BookApplications', getBookApplications));

const today = new Date().toISOString().split('T')[0];

function handleButtonClick(option, actionFunction) {
    const buttons = document.querySelectorAll('.nav-area > button');

    buttons.forEach(btn => btn.classList.remove('select'));
    document.getElementById(`btn${option}`).classList.add('select');

    actionFunction();
}

const getCurrentlyBorrowedBooks = () => {
    pagination.innerHTML = '';

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
    pagination.innerHTML = '';

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
    pagination.innerHTML = '';

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
    pagination.innerHTML = '';
    
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

const cancelBookReservation = id => cancelHttpRequest("/api/study/book/reservation", id);

window.onload = () => handleButtonClick('CurrentlyBorrowedBooks', getCurrentlyBorrowedBooks);
