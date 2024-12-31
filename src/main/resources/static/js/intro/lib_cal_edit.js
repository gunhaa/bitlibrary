/* 편집 모달 입력 변수 */
const scheduleName = document.getElementsByName("scheduleName")[0];
const startDate = document.getElementsByName("startDate")[0];
const endDate = document.getElementsByName("endDate")[0];
const calCateSelect = document.getElementById("calendarCategory");
const calCateOption = document.getElementById("calendarCategory").options;

/* 편집 등록 버튼 */
const confirmBtn = document.getElementById("edit-confirm_btn");

/* 알림창 변수 선언 */
/* 알림창 전체 */
const popupNoticeLayer = document.getElementById("popup_layer");

/* 알림창 내용 */
const popupNoticeContent = document.querySelector(".popup_content > p");

/* 알림창 확인 버튼 */
const popupNoticeConfirmBtn = document.getElementById("confirm_btn")


/* 캘런더 */
const todayBtn = document.getElementById("today-btn");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const dateDiv = document.getElementById("date-div");

/* 선택된 날짜 */
let selectDate;

/* 오늘 날짜 */
const todayDate = new Date();

/* 오늘 날짜 모양 포맷 */
const dateFormat = ((todayDate.getMonth()+1) < 10 ? "0" + (todayDate.getMonth()+1) : (todayDate.getMonth()+1))
                + "월 " + ((todayDate.getDate()) < 10 ? "0" + (todayDate.getDate()) : (todayDate.getDate()) ) + "일";

/* 오늘 날짜 모양 포맷2 */
const dateFormat2 = todayDate.getFullYear() + "-" + ((todayDate.getMonth()+1) < 10 ? "0" + (todayDate.getMonth()+1) : (todayDate.getMonth()+1))
+ "-" + ((todayDate.getDate()) < 10 ? "0" + (todayDate.getDate()) : (todayDate.getDate()) ) ;


/* 오늘 포맷된 날짜, 캘랜더, 일정 보여줌 */
(function(){
    dateDiv.innerText = dateFormat;
    selectDate = dateFormat2;
    getTypeCal();
    createSchedule(dateFormat2);
})();


/* 캘랜더 만드는 전역 변수 */
var calendarEl;
var calendar ;

/* 캘런더 오늘 버튼 */
todayBtn.addEventListener("click",function() {
  calendar.today();
  createSchedule(dateFormat2);
  dateDiv.innerText = dateFormat;
  selectDate = dateFormat2;
})

/* 캘런더 이전 버튼 */
prevBtn.addEventListener("click",function(){ calendar.prev();})

/* 캘런더 next 버튼 */
nextBtn.addEventListener("click",function() {calendar.next();})

/* 일정 등록 메소드 */
function insertSchedule(){

  /* 일정 이름이 없을시 */
  if(scheduleName.value.trim().length == 0){
    createNoticeMessage("일정 이름이 없습니다.", 0, startDate.value, "insert");
    scheduleName.focus();
    return;
  }

  /* 일정 시작 일이 없을 시 */
  if(startDate.value.trim().length == 0){
    createNoticeMessage("일정 시작일이 없습니다.", 0, startDate.value, "insert");
    startDate.focus();
    return;
  }

  /* 선택된 일정 종류 담을 변수 */
  let calType;

  /* 선택된 일정 종류 값 찾은후 담기 */
  for(let i = 0; i < calCateOption.length; i++){
    if(calCateOption[i].selected == true){
        calType = calCateOption[i].value;
    }
  }
  
  /* fetch 보낼 객체 */
  const insertCal = {
    calendarName : scheduleName.value,
    startDt : startDate.value,
    endDt : endDate.value,
    calendarType : calType
  }

  /* 비동기로 insert 보내기 */
  fetch("/intro2/insertShedule",{
    method : "POST",
    headers : {"Content-type" : "application/json"},
    body : JSON.stringify(insertCal)
  })
  .then(resp => resp.text())
  .then(result => {
    if(result > 0){ /* insert 성공시 */
      createNoticeMessage("일정 추가를 성공했습니다", result, startDate.value, "insert");

    }else{ // insert 실패시
      createNoticeMessage("일정 추가를 실패했습니다", result, startDate.value, "insert");
    }
  })
  .catch(e => {console.log(e)})
}

/* 일정 수정 메소드 */
function updateSchedule(calNo) {

  /* 일정 이름 없을시 */
  if(scheduleName.value.trim().length == 0){
    const re = 0;
    createNoticeMessage("일정 이름이 없습니다.", re, startDate.value, "update");
    console.log("test1");
    return;
  }

  /* 일정 시작일 없을시 */
  if(startDate.value.trim().length == 0){
    const re = 0;
    createNoticeMessage("일정 시작일이 없습니다.", re, startDate.value, "update");
    return;
  }

  /* 선택된 일정 종류 담을 변수 */
  let calType;

  /* 선택된 일정 종류 찾은 다음 담음 */
  for(let i = 0; i < calCateOption.length; i++){
    if(calCateOption[i].selected == true){
        calType = calCateOption[i].value;
    }
  }
  
  /* 비동기로 보낼 객체 */
  const updateCal = {
    calendarNo : calNo,
    calendarName : scheduleName.value,
    startDt : startDate.value,
    endDt : endDate.value,
    calendarType : calType
  }

  /* 비동기로 update */
  fetch("/intro2/updateShedule",{
    method : "PUT",
    headers : {"Content-type" : "application/json"},
    body : JSON.stringify(updateCal)
  })
  .then(resp => resp.text())
  .then(result => {
    if(result > 0){ // update 성공시
      createNoticeMessage("일정 수정를 성공했습니다", result, startDate.value, "update");

    }else{ // update 실패시
      createNoticeMessage("일정 수정를 실패했습니다", result, startDate.value, "update");
    }
  })
  .catch(e => {console.log(e)})

}

/* 일정 삭제 메소드 */
function deleteShedule(calNo, delDate) {

  /* 비동기로 일정 삭제 */
  fetch("/intro2/deleteShedule",{
    method : "DELETE",
    headers : {"Content-type" : "application/json"},
    body : calNo
  })
  .then(resp => resp.text())
  .then(result => {
    if(result > 0){ // 일정 삭제 성공시
      createNoticeMessage("일정 삭제를 성공했습니다", result, delDate, "delete");
      selectDate = delDate;

    }else{ // 일정 삭제 실패시
      createNoticeMessage("일정 삭제를 실패했습니다", result, delDate, "delete");
      selectDate = delDate;
    }
  })
  .catch(e => {console.log(e)}) 
}

/* 알림창 가져오는 메소드 */
function createNoticeMessage(message, r, date, mType){

  /* 캘랜더 다시 생성 */
  getTypeCal();

  /* 캘랜더 스케줄 다시 생성 */
  createSchedule(date);

  if(mType === "delete"){ // 삭제 일경우
    delPop.style.display = "none";
  }else{// 추가, 수정일 경우
    editPopup.style.display = "none";
  }

  /* 알림창 띄움 */
  popupNoticeLayer.style.display = "block";
  popupNoticeContent.innerHTML = message;
  popupNoticeConfirmBtn.setAttribute("onClick", "noticeConClick("+r+",'"+mType+"')")

}

/* 알림창 닫기 버튼 */
function noticeConClick(result, mType){
  popupNoticeLayer.style.display = "none";

  /* 실패시 다시 편집창, 삭제창 띄어줌 */
  if(result == 0){
    if(mType === "delete"){
      delPop.style.display = "block";
    }else{
      editPopup.style.display = "block";
    }
  }
}

/* 캘런더 이번트 가져오는 메소드 */
function getTypeCal(){

  /* 휴관일, 행사 이벤트 가져오는 비동기 */
  fetch("/intro2/getHECal")
  .then(resp => resp.json())
  .then( eventList => {

    /* 전체 이벤트를 담을 배열 */
    let events = [];

    for(let e of eventList){
        /* 값 하나하나 담을 객체 */
        let event = {
            title : e.calendarName,
            start : e.startDt
        };

        /* 종료일이 있다면 필드값 넣기 */
        if(e.endDt != null){
            event.end = e.endDt;
        }

        /* 타입별로 색 넣어주기 */
        if(e.calendarType == 2){
            event.color = "#d4d4d4";
            event.textColor = "#ffffff";
            event.rendering = "background";
        }

        if(e.calendarType == 3){
            event.color = "#5547ff";
            event.textColor = "#ffffff";
        }

        /* events 안에 event 넣기 */
        events.push(event);
    }

    /* 캘런더 생성 메소드 */
    createCalender(events);

  })
}

/* 캘런더 생성 메소드 */
function createCalender(event) {

  /* 캘랜더 담을 id */
  calendarEl = document.getElementById('calendar');

  /* 진자 캘랜더 생성 객체 */
  calendar = new FullCalendar.Calendar(calendarEl, {
  initialView: 'dayGridMonth',
  headerToolbar:{
    start : 'title',
    center : "",
    end : ''
  },
  locale : 'ko',
  titleFormat : function(date){
    return date.date.year + "년 " + (parseInt(date.date.month) + 1) + "월";
  },
    // 일 빼기
dayCellContent : function(info){
    var number=document.createElement("a");
    number.classList.add("fc-daygrid-day-number");
    number.innerHTML =info.dayNumberText.replace("일","");
    return {
        html:number.outerHTML
    };
},
events: event ,
  selectable : true,/* 캘랜더 날짜 선택 */
 
    /* 캘런더 날짜 선택 시 함수 수행*/
  dateClick : function(e){
    selectDate = e.dateStr;

    createSchedule(selectDate);
    /* e.dateStr - 클릭한 날짜 가져옴 */

    /* 선택한 날짜에서 금요일 구하는 코드
    let day = new Date(selectDate).getDay();
    console.log(day);
    if(day == 5){
        alert("금요일은 선택할 수 없습니다.");
        return;
    } */
  }
  
});

/* 캘랜더 화면에 보여줌
  이 구문 없으면 안보임
*/
calendar.render();

}

/* 선택한 날짜 일정 담을 tbody */
const tbody = document.querySelector(".todo-surround > table > tbody");

/* 일정 목록 생성 */
function createSchedule(date){

  let selectArray = date.split("-");
  console.log(selectArray);
  dateDiv.innerText = selectArray[1] + "월 " + selectArray[2] + "일";

  fetch("/intro2/selectGetDate?date="+date)
  .then(resp => resp.json())
  .then(result =>{
    console.log(result);
    /* 일정 리스트를 담아둘 tbody */
    tbody.innerHTML = "";
    if(result != null){

      for(let r of result){

        /* 일정 담을 tr */
        const tr = document.createElement("tr");

        /* 일정 제목 */
        const eventTitle = document.createElement("td");
        eventTitle.innerText = r.calendarName;

        /* 일정 시작일 */
        const eventStart = document.createElement("td");
        eventStart.innerText = r.startDt;

        /* 일정 종료일 */
        const eventEnd = document.createElement("td");
        if(r.endDt != null){
          eventEnd.innerText = r.endDt;
        }

        /* 일정 타입 */
        const eventType = document.createElement("td");
        if(r.calendarType == 2){
          eventType.innerText = "휴관일";
        }else{
          eventType.innerText = "행사";
        }

        /* 비고(버튼 영역) */
        const eventBtnArea = document.createElement("td");

        /* 수정 버튼 */
        const upBtn = document.createElement("button");
        upBtn.innerText = "수정"
        upBtn.classList.add("scheduleUpdate");
        upBtn.setAttribute("onClick","openPopUpdate(this,"+ r.calendarNo +")")

        /* 삭제 버튼 */
        const delBtn = document.createElement("button");
        delBtn.innerText = "삭제";
        delBtn.classList.add("scheduleDelete");
        delBtn.setAttribute("onClick","openDelPopUp(" + r.calendarNo +",'"+ r.startDt +"')");
        console.log(r.startDt);

        eventBtnArea.append(upBtn,delBtn);

        /* tr 삽입 */
        tr.append(eventTitle,eventStart,eventEnd,eventType,eventBtnArea);

        /* tbody에 tr 삽입 */
        tbody.append(tr);
      }

    }

  })
  .catch(e => {console.log(e)})

}

/* ---------------------------------------------------------------------------------------------------------------- */
/* 편집 모달 열기 */
const editPopup = document.getElementById("edit-popup_layer");

document.getElementById("add-todo").addEventListener("click",() => {
    editPopup.style.display = "block";

    scheduleName.value = '';
    startDate.value = '';
    endDate.value = '';
    document.getElementsByTagName("option")[0].selected = true;

    startDate.value = selectDate;
    confirmBtn.setAttribute("onClick", "insertSchedule()");
});

/* 편집 모달 닫기 */
const editPopupClose = document.getElementById("edit-popup-close");
editPopupClose.addEventListener("click",() => {
    editPopup.style.display = "none";
    scheduleName.value = '';
    startDate.value = '';
    endDate.value = '';
    document.getElementsByTagName("option")[0].selected = true;
    confirmBtn.setAttribute("onClick","insertSchedule()");
});

/* 수정 버튼 클릭시 */
function openPopUpdate(e,calNo){
    const parentNode = e.parentElement;
    const calCate = parentNode.previousElementSibling;
    for(let i = 0; i < calCateOption.length; i++){
        if(calCateOption[i].innerText == calCate.innerText){
            calCateOption[i].selected = true;
        }
    }

    const endDt = calCate.previousElementSibling;
    endDate.value = endDt.innerText;

    const startDt = endDt.previousElementSibling;
    startDate.value = startDt.innerText;

    const scheduleNm = startDt.previousElementSibling;
    scheduleName.value = scheduleNm.innerText;

    confirmBtn.setAttribute("onClick","updateSchedule(" + calNo + ")");

    editPopup.style.display = "block";

}

const delPop = document.getElementById("del-popup_layer");

/* 삭제 알람창 취소 클릭시 */
document.getElementById("del-cancel_btn").addEventListener("click",() => {
  delPop.style.display = "none";
})

/* 삭제 알림창 열기 함수 */
function openDelPopUp(calNo, date){
  delPop.style.display = "block";

  document.getElementById("del-confirm_btn").setAttribute("onClick","deleteShedule("+calNo+",'"+date+"')");
}


