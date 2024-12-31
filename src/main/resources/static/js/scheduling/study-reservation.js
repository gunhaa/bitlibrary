let selectedSeat = null; // 좌석 선택 상태를 저장하는 전역 변수

function updateSeats(e) {
    if (e) e.preventDefault();
    const reservationDt = document.getElementById("reservationDt").value;
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;

    if (!reservationDt || !startTime || !endTime) {
        alert("예약 일자와 시간을 모두 선택해 주세요.");
        return;
    }

    const data = {
        reservationDt: reservationDt,
        startTime: startTime,
        endTime: endTime
    };

    fetch('/reservation/study', {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(seats => {
       
        console.log(seats);

        const seatSection = document.getElementById("seatSection");
        seatSection.innerHTML = ''; // 기존 좌석 초기화

        let totalSeats = 24; // 전체 좌석 수
        let usedSeats = 0;   // 사용 중인 좌석 수
        let availableSeats = 0; // 잔여 좌석 수

        const seatGroups = [[], [], []];

        seats.forEach(seat => {
            if (seat.seatNo >= 1 && seat.seatNo <= 8) {
                seatGroups[0].push(seat);
            } else if (seat.seatNo >= 9 && seat.seatNo <= 16) {
                seatGroups[1].push(seat);
            } else if (seat.seatNo >= 17 && seat.seatNo <= 24) {
                seatGroups[2].push(seat);
            }

            if (seat.seatStatus === '사용중') {
                usedSeats++;
            } else if (seat.seatStatus === '공석') {
                availableSeats++;
            }
        });

        seatGroups.forEach(group => {
            const groupDiv = document.createElement('div');
            groupDiv.className = 'seat-group';

            group.forEach(seat => {
                const table = document.createElement('table');
                table.className = seat.seatStatus === '사용중' ? 'private' : '';
                table.innerHTML = `
                    <tr><th>${seat.seatNo}</th></tr>
                    <tr><td>${seat.seatStatus}</td></tr>
                `;

                if (seat.seatStatus === '공석') {
                    table.addEventListener('click', () => {
                        if (selectedSeat) {
                            selectedSeat.classList.remove('private');
                            selectedSeat.classList.remove('selected');
                        }

                        if (selectedSeat === table) {
                            selectedSeat = null;
                            document.getElementById("seatNo").textContent = "좌석 번호: --";
                        } else {
                            selectedSeat = table;
                            table.classList.add('private');
                            table.classList.add('selected');
                            document.getElementById("seatNo").textContent = `좌석 번호: ${seat.seatNo}`;
                        }
                    });
                }

                groupDiv.appendChild(table);
            });

            seatSection.appendChild(groupDiv);
        });

        document.getElementById("totalSeats").textContent = totalSeats;
        document.getElementById("usedSeats").textContent = usedSeats;
        document.getElementById("availableSeats").textContent = availableSeats;
    })
    .catch(e => console.log(e));
}

function bookSeat() {

    if (!selectedSeat) {
        alert("좌석을 선택해 주세요.");
        return;
    }

    const reservationDt = document.getElementById("reservationDt").value;
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;
    const seatNo = selectedSeat.querySelector("th").textContent;

    if (!reservationDt || !startTime || !endTime || !seatNo) {
        alert("예약 정보를 모두 입력해 주세요.");
        return;
    }

    const alertModal = document.getElementById("alert_modal");
    const alertContent = document.getElementById("alert_content");

    alertContent.innerHTML = `
        <p>좌석 ${seatNo}을 예약 하시겠습니까?</p>
    `;
    
    alertModal.style.display = 'block';

    document.getElementById("confirm_btn").addEventListener("click", () => {
        // 확인 버튼 클릭 시 예약 처리
        const reservationData = {
            reservationDt: reservationDt,
            startTime: startTime,
            endTime: endTime,
            seatNo: seatNo
        };

        fetch('/reservation/study/book', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(reservationData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.result === 1) {
                showReservationSuccessModal(seatNo, reservationDt, startTime, endTime);
                alertModal.style.display = 'none'; // 예약 완료 후 모달 닫기
            } else {
                alert("예약 실패. 다시 시도해 주세요.");
            }
        })
        .catch(error => {
            console.error('예약 처리 중 오류 발생:', error);
            alert("예약 처리 중 오류가 발생했습니다.");
        });
    });

    document.getElementById("cancel_btn").addEventListener("click", () => {
        alertModal.style.display = 'none'; // 취소 버튼 클릭 시 모달 닫기
        location.href = location.href;
    });
}

function showReservationSuccessModal(seatNo, reservationDt, startTime, endTime) {
    const reservationModal = document.getElementById("reservation_modal");
    const reservationContent = document.getElementById("reservation_content");

    reservationContent.innerHTML = `
        <p>예약 좌석 : ${seatNo}번</p>
        <p>예약 일자 : ${reservationDt}</p>
        <p>예약 시간 : ${startTime} ~ ${endTime}</p>
    `;
    
    reservationModal.style.display = 'block';
}

const reservHistoryBtn = document.getElementById("reserv_history");
reservHistoryBtn.addEventListener("click", function() {
    // 예약 이력 페이지로 이동
    location.href = "/myLibrary/reserv";
});


// input="time" 단위 1시간으로 맞추기 + 운영시간 07:00 ~ 23:00 제한
document.getElementById('startTime').addEventListener('change', function() {
    validateTime('start');  // type을 'start'로 전달
});

document.getElementById('endTime').addEventListener('change', function() {
    validateTime('end');  // type을 'end'로 전달
});

function validateTime(type) {
    const startTimeInput = document.getElementById("startTime");
    const endTimeInput = document.getElementById("endTime");

    const startTime = startTimeInput.value;
    const endTime = endTimeInput.value;

    const minTime = "07:00";
    const maxTime = "23:00";

    if (type === 'start' && startTime) {
        if (startTime < minTime) startTimeInput.value = minTime;
        if (startTime > maxTime) startTimeInput.value = maxTime;
    }

    if (type === 'end' && endTime) {
        if (endTime < minTime) endTimeInput.value = minTime;
        if (endTime > maxTime) endTimeInput.value = maxTime;
        if (endTime <= startTime) {
            alert("종료 시간이 시작 시간보다 늦어야 합니다.");
            endTimeInput.value = ""; // 조건에 맞지 않으면 endTime을 초기화
        }
    }
}

// 1시간 단위로 강제하는 함수
function enforceTimeStep(input) {
    const value = input.value;
    if (!value) return;

    // 시와 분으로 나눕니다.
    const [hours, minutes] = value.split(':').map(Number);

    // 분이 00분이 아니라면 강제로 00분으로 변경합니다.
    if (minutes !== 0) {
        const correctedTime = `${hours.toString().padStart(2, '0')}:00`;
        input.value = correctedTime;
    }
}