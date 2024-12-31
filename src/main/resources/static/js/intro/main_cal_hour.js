/* 캘랜더 위에 버튼 및 년월 포맷 */
const todayBtn = document.getElementById("today-btn");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const dateDiv = document.getElementById("date-div");

/* 오늘 날짜 */
const todayDate = new Date();

/* 오늘 날짜 모양 포맷 */
const dateFormat = ((todayDate.getMonth()+1) < 10 ? "0" + (todayDate.getMonth()+1) : (todayDate.getMonth()+1))
+ "월 " + ((todayDate.getDate()) < 10 ? "0" + (todayDate.getDate()) : (todayDate.getDate()) ) + "일";

/* 오늘 날짜 모양 포맷2 */
const dateFormat2 = todayDate.getFullYear() + "-" + ((todayDate.getMonth()+1) < 10 ? "0" + (todayDate.getMonth()+1) : (todayDate.getMonth()+1))
+ "-" + ((todayDate.getDate()) < 10 ? "0" + (todayDate.getDate()) : (todayDate.getDate()) ) ;
console.log(dateFormat2);

/*오늘 포맷된 날짜, 캘랜더, 일정 보여줌 */
(function(){
    dateDiv.innerText = dateFormat;
    typeCalendar(1);
})();

/* 선택된 날짜 저장변수 */
let selectDate;

/* 캘런더 만드는 변수 전역변수로 선언 */
var calendarEl = document.getElementById('calendar');
var calendar;


/* 캘랜더 타입별로 이벤트 가져오는 함수 */
function typeCalendar(type){

    fetch("/intro2/getTypeCalendar?type="+type)
    .then(resp => resp.json())
    .then(eventList => {
        /* 전체 이벤트를 담을 배열 */
        let events = [];

        for(let e of eventList){
            /* 값 하나하나 담을 객체 */
            let event = {
               /*  title : e.calendarName, - 캘랜더 일정 너무 작아서 주석*/
                start : e.startDt
            };

            /* 종료일이 있다면 필드값 넣기 */
            if(e.endDt != null){
                event.end = e.endDt;
            }

            /* 타입별로 색 넣어주기 */
            if(type == 1){
                event.color = "#FF0000";
                event.textColor = "#ffffff";
            }

            if(type == 2){
                event.color = "#d4d4d4";
                event.textColor = "#ffffff";
                event.rendering = "background";
            }

            if(type == 3){
                event.color = "#5547ff";
                event.textColor = "#ffffff";
            }

            if(type == 4){
                event.color = "#FFFF00";
                event.textColor = "#000000";
            }

            /* events 안에 event 넣기 */
            events.push(event);
        }

        console.log(events);
        /* 캘런더 생성 메소드 */
        createCalender(events, type);
    })
}

/* 캘런더 생성 메소드 */
function createCalender(event, type) {
    
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
    console.log(selectDate);
    let selectArray = selectDate.split("-");
    console.log(selectArray);
    dateDiv.innerText = selectArray[1] + "월 " + selectArray[2] + "일";
    /* e.dateStr - 클릭한 날짜 가져옴 */

    showShedule(selectDate, type);


    }

    
});

/* 캘랜더 화면에 보여줌
  이 구문 없으면 안보임
*/
calendar.render();

}

/* 캘런더 오늘 버튼 */
todayBtn.addEventListener("click",function() {
    calendar.today();
    dateDiv.innerText = dateFormat;
})

/* 캘런더 이전 버튼 */
prevBtn.addEventListener("click",function(){ calendar.prev();})

/* 캘런더 next 버튼 */
nextBtn.addEventListener("click",function() {calendar.next();})

/* 메인 모달 전체 영역 */
const mainCalPopUp = document.getElementById("main_popup_layer");

/* 메인 모달 박스 영역 */
const mainCalPopBox = document.getElementsByClassName("main_popup_box")[0];

/* 메인 모달 헤더 영역 */
const mainCalHeader = document.querySelector(".main_popup_header > p");

/* 메인 모달 내용 영역 */
const mainCalPopContent = document.querySelector(".main_popup_content > div > table > tbody");

/* 메인 모달 확인 버튼 */
const mainCalConfirmBtn = document.getElementById("main_confirm_btn");

/* 일정이 있을시 모달에 내용 추가 및 모달 열어줌 */
function showShedule(date, type){

    const cal = {
        "startDt" : date,
        "calendarType" : type
    };

    fetch("/intro2/getDateCal",{
        method : "POST",   
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(cal) 
    })
    .then(resp => resp.json())
    .then(result => {
        
        if(result != ""){
            mainCalPopContent.innerHTML = "";
            mainCalHeader.innerHTML = date;

            for(let r of result){

                /* 일정 tr */
                const calTr = document.createElement("tr");

                /* 일정 종류 td */
                const calTd = document.createElement("td");

                /* 일정 종류 색 넣기 div */
                const catColor = document.createElement("div");
                catColor.classList.add("cat_color");

                /* 타입에 따라 색 변환 */
                if(type == 1){catColor.classList.add("gong");}
                if(type == 2){catColor.classList.add("hue");}
                if(type == 3){catColor.classList.add("eventDay");}
                if(type == 4){catColor.classList.add("classDay");}

                /* 일정 종류명 span */
                const catName = document.createElement("span");

                /* 타입 따라 일정 종류명 변환 */
                if(type == 1){catName.innerHTML=" 공휴일";}
                if(type == 2){catName.innerHTML=" 휴관일";}
                if(type == 3){catName.innerHTML=" 행사";}
                if(type == 4){catName.innerHTML=" 클래스";}

                /* 일정 종류 td에 값 넣기 */
                calTd.append(catColor, catName);

                /* 일정 td */
                const sheduleTd = document.createElement("td");
                sheduleTd.innerHTML = r.calendarName;

                /* 일정 tr에 td들 넣기 */
                calTr.append(calTd, sheduleTd);

                /* 내용 영역에 tr 넣기 */
                mainCalPopContent.append(calTr);

            }

            mainCalConfirmBtn.setAttribute("onClick", "closeMainCalPop()");

            mainCalPopUp.style.display = "block";

        }else{
            mainCalPopUp.style.display = "none";
        }
    } )
    .catch(e => {console.log(e)})
}



function closeMainCalPop(){
    mainCalPopUp.style.display = "none";
}

/* 메인 모달 외 영역 클릭시 사라짐 */
document.addEventListener("click",e => {
    if(e.target.contains(mainCalPopBox)){
        closeMainCalPop();
    }
})
