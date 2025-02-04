const btnSeatReservations = document.getElementById('btnSeatReservations');
const btnSpaceReservations = document.getElementById('btnRoomReservations');
const btnClassReservations = document.getElementById('btnClassApplicants');

btnSeatReservations.addEventListener('click', () => handleButtonClick('SeatReservations', getSeatReservations));
btnSpaceReservations.addEventListener('click', () => handleButtonClick('RoomReservations', getRoomReservations));
btnClassReservations.addEventListener('click', () => handleButtonClick('ClassApplicants', getClassApplicants));

function handleButtonClick(option, actionFunction) {
    const buttons = document.querySelectorAll('.nav-area > button');

    buttons.forEach(btn => btn.classList.remove('select'));
    document.getElementById(`btn${option}`).classList.add('select');

    actionFunction();
}

const getSeatReservations = () => {
    fetchData("/api/study/seats/reservation", data => {
        updateTable(
            [
                { text: "번호" },
                { text: "구분" },
                { text: "예약일" },
                { text: "예약시간" },
                { text: "취소" }
            ],
            data.map(item => `<tr>
                <td>${item.seatNo}</td>
                <td>디지털열람실</td>
                <td>${item.createdDate}</td>
                <td>${item.reservationStart} ~ ${item.reservationEnd}</td>
                <td><button onclick="cancelSeatReservation(${item.id})">취소</button></td>
            </tr>`),
            "대출중인 도서가 없습니다"
        );
    }, "대출 중인 도서를 불러오는 데 실패했습니다.");
};

const getRoomReservations = () => {
    fetchData("/api/study/rooms/reservation", data => {
        updateTable(
            [
                { text: "구분" },
                { text: "예약일" },
                { text: "예약시간" },
                { text: "취소" }
            ],
            data.map(item => `<tr>
                <td>세미나실</td>
                <td>${item.createdDate}</td>
                <td>${item.reservationStart} ~ ${item.reservationEnd}</td>
                <td><button onclick="cancelRoomReservation(${item.id})">취소</button></td>
            </tr>`),
            "이전 대출내역이 없습니다"
        );
    }, "이전 대출내역를 불러오는 데 실패했습니다.");
};

const getClassApplicants = () => {
    fetchData("/api/study/classes/applicant", data => {
        updateTable(
            [
                { text: "클래스명" },
                { text: "클래스 시작/종료일", style: "width: 230px;" },
                { text: "신청기간", style: "width: 230px;" },
                { text: "취소", style: "width: 70px;" }
            ],
            data.map(item => `<tr>
                <td><a href="/scheduling/12/${item.id}">${item.title}</a></td>
                <td>${item.startDate} ~ ${item.endDate}</td>
                <td>${item.recruitmentStartDate} ~ ${item.recruitmentEndDate}</td>
                <td><button onclick="cancelClassApplicant(${item.id})">취소</button></td>
            </tr>`),
            "예약 내역이 없습니다"
        );
    }, "예약내역를 불러오는 데 실패했습니다.");
};

const cancelSeatReservation = id => cancelHttpRequest("/api/study/seat/reservation", id);
const cancelRoomReservation = id => cancelHttpRequest("/api/study/room/reservation", id);
const cancelClassApplicant = id => cancelHttpRequest("/api/study/class/applicant", id);

window.onload = () => handleButtonClick('SeatReservations', getSeatReservations);
