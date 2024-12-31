/* 삭제 알림창 모달 */
const delPopupLayer = document.getElementById("del-popup_layer");
const delCancelPopup = document.getElementById("del-cancel_btn");

/* 삭제 알림창 모달 열기 */
function openModal(e){
    delPopupLayer.style.display = "block";
}

/* 삭제 함수 */
function deleteLibAn(boardNo){
    location.href = "/intro/1/7/28/delete/"+boardNo;
}

/* 삭제 알림창 모달 닫기 */
delCancelPopup.addEventListener("click",() => {
    delPopupLayer.style.display = "none";
});

/* 편집 모달 */
const editPopup = document.getElementById("edit-popup_layer");
const editPopupClose = document.getElementById("edit-popup-close");

/* 모달 input들 */
const libraryName = document.querySelector('input[name="boardTitle"]');
const libraryIntroText = document.querySelector('textarea[name="boardContent"]');
let addressInput = document.getElementById("libraryAddress");
const searchMap = document.getElementById("searchMap");


/* 편집 모달 열기 */
function openEditModal(e){
    editPopup.style.display = "block";
    
    libraryName.value = "";
    libraryIntroText.value = "";
    addressInput.value = "";

    for(let i = 0; i < preview.length; i++){
        preview[i].setAttribute("src","");
    }
    

    /* 모달 지도 초기화 */
    map2.relayout();
    map2.setCenter(mapOptions.center);
    map.setLevel(mapOptions.level);

    editLibFrm.setAttribute("action","/intro/1/7/28/insert");
    
}

/* 편집 모달 닫기 */
editPopupClose.addEventListener("click",() => {
    editPopup.style.display = "none";

    libraryName.value = "";
    libraryIntroText.value = "";
    addressInput.value = "";

    for(let i = 0; i < preview.length; i++){
        preview[i].setAttribute("src","");
    }

});




// 즉시 실행 함수 : 성능 up, 변수명 중복 X
(function(){
    const boardImg = document.querySelectorAll(".boardImg > img");
    console.log(boardImg);

    if(boardImg.length > 0){//목록에 썸네일 이미지가 있을 경우
        console.log(boardImg);
        const modal = document.querySelector('.modal');
        const modalClose = document.getElementById("modal-close");
        const modalImage = document.getElementById("modal-image");

        for(let thum of boardImg){
            thum.addEventListener("click",function(){
                modalImage.setAttribute("src", thum.getAttribute("src"));

                /* on/off 스위치 */
                // classList.toggle("클래스명") : 클래스가 없으면 추가(add)
                //                               클래스가 있으면 제거(remove)
                modal.classList.toggle("show"); // add
                
            });

}

// x 버튼
modalClose.addEventListener("click", function(){
    modal.classList.toggle("hide"); //hide 클래스 추가

    setTimeout(function(){ // 0.45초 후 동작
        modal.classList.toggle('hide'); // hide 클래스 제거

        modal.classList.toggle('show');// show 클래스 remove
    }, 450);
    });
    }
})();

/* 지도 */
const libraryLat = document.getElementsByName("libraryLat")[0];
const libraryLng = document.getElementsByName("libraryLng")[0];



let mapCur = document.getElementById("map");
if(mapCur != null){
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(libraryLat.value, libraryLng.value),
    level: 3
};

var map = new kakao.maps.Map(container, options);

// 마커가 표시될 위치입니다 
var markerPosition  = new kakao.maps.LatLng(libraryLat.value, libraryLng.value); 

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);
}


// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
// marker.setMap(null);   

/* ---------------------------------------------------------------------- */

/* 편집 수정 모달 열기 */
/* 편집 수정 모달 열기 */
const curLibraryNm = document.querySelector(".libraryAnother.current > a");
const curLibraryAdd = document.getElementById("anLibraryAddress");
const curLibraryIntro = document.getElementById("anLibraryIntro");
const curLibraryLat = document.getElementsByName("libraryLat")[0];
const curLibraryLng = document.getElementsByName("libraryLng")[0];
const editLibFrm = document.getElementsByName("editIntroFrm")[0];

/* 경로 */
const curLat = document.querySelector("[name='searchLat']");
const curLng = document.querySelector("[name='searchLng']");

/* 편집 이미지 */
const img1 = document.getElementById("img1");
const img2 = document.getElementById("img2");
const img3 = document.getElementById("img3");
const img4 = document.getElementById("img4");

/* 지도의 위도 경도 */
let position;
let lat = 33.450701;
let lng = 126.570667;

function openUpdateModal(e, boardNo){
    editPopup.style.display = "block";

    editLibFrm.setAttribute("action","/intro/1/7/28/update" +"/" + boardNo);

    /* 모달 지도 초기화 */
    map2.relayout();
    map2.setCenter(mapOptions.center);
    map.setLevel(mapOptions.level);

    libraryName.value = curLibraryNm.innerText;
    libraryIntroText.value = curLibraryIntro.innerText;
    addressInput.value = curLibraryAdd.innerText;

    curLat.value = libraryLat.value;
    curLng.value = libraryLng.value;

    fetch("/intro/getImageList?boardNo="+boardNo)
    .then(resp => resp.json())
    .then(imageList => {
        if(imageList != null){
            for(let img of imageList){
                preview[img.imageOrder].setAttribute("src", img.imagePath + img.imageReName);
            }
        }
    })
    .catch(e => {console.log(e)})
    
}



(function(){
    if(libraryLat != null && libraryLng != null){
        lat = libraryLat.value;
        lng = libraryLng.value;
    }
})();

/* 편집 팝업 주소 검색 */
var mapContainer = document.getElementById('map2');
var mapOptions = {
    center: new kakao.maps.LatLng(lat, lng),
    level: 3
};

var map2 = new kakao.maps.Map(mapContainer, mapOptions);

// 마커가 표시될 위치입니다 
var markerPosition3  = new kakao.maps.LatLng(lat, lng); 

// 마커를 생성합니다
var marker2 = new kakao.maps.Marker({
    position: markerPosition3
});

// 마커가 지도 위에 표시되도록 설정합니다
marker2.setMap(map2);

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

searchMap.addEventListener("click", function() {
    geocoder.addressSearch(addressInput.value, function(result,status){
        
    // 정상적으로 검색이 완료됐으면 
    if (status === kakao.maps.services.Status.OK) {
        marker2.setMap(null);
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        marker2 = new kakao.maps.Marker({
            map: map2,
            position: coords
        });

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map2.setCenter(coords);

        position = map2.getCenter();
        console.log(position);

        lat = position.getLat();
        lng = position.getLng();
        console.log("lat : " + lat);
        console.log("lng : " + lng);

        curLat.value = lat;
        curLng.value = lng;

    }

    });
});

/* ---------------------------------------------------------------------------------------------------------------------- */
/* 모달 이미지 추가및 삭제 */
// 미리보기 관련 요소 모두 얻어오기
// --> 동일한 개수의 요소가 존재함 == 인덱스가 서로 일치함
const preview = document.getElementsByClassName("preview");
const inputImage = document.getElementsByClassName("inputImage");
const deleteImage = document.getElementsByClassName("delete-image");

// 게시글 수정 시 삭제된 이미지의 레벨(위치)를 저장할 input 요소
const deleteList = document.getElementById("deleteList");

// 게시글 수정 시 삭제된 이미지의 레벨(위치)를 기록해둘 set 생성
const deleteSet = new Set(); // 중복 허용 X, 순서 X 
                            // -> 여러번 클릭 시 중복 값 저장 방지

for(let i = 0; i < inputImage.length; i++){

    // 파일이 선택 되었을 때
    inputImage[i].addEventListener("change",function(){

        if(this.files[0] != undefined){

            const reader = new FileReader(); 
            // 선택된 파일을 읽을 객체 생성

            reader.readAsDataURL(this.files[0]);
            // 지정된 파일을 읽음 -> result에 저장(URL 포함)
            // -> URL을 이용해서 이미지 볼 수 있음

            reader.onload = function(e){// reader가 파일을 다 읽어온 경우
                // e.target == reader
                // e.target.result == 읽어들인 이미지의 URL
                // preview[i] == 파일이 선택된 input 태그와 인접한 preview이미지 태그

                preview[i].setAttribute("src", e.target.result);

                // 이미지가 불러와졌을 때
                // deleteSet에서 해당 레벨을 제고(삭제 목록에서 제외)
                deleteSet.delete(i);
            }

        }else{ // 파일이 선택이 되지 않았을 때 (취소)
            preview[i].removeAttribute("src");
            deleteSet.add(i);
        }

    });

    /* 미리보기 삭제 버튼(X)이 클릭 되었을 때 */
    deleteImage[i].addEventListener("click",function(){
        
        // 미리보기가 존재하는 경우에만(이전에 추가된 이미지가 있을 때만) x버튼 동작
        if(preview[i].getAttribute("src") != ""){
            /* 미리보기 삭제 */
            preview[i].removeAttribute("src");
    
            /* input의 값 ""만들기 */
            inputImage[i].value = "";
    
            // deleteSet에 삭제된 이미지 레벨(i) 추가
            deleteSet.add(i);

        }


    });
}


/* 알림창 변수 선언 */
/* 알림창 전체 */
const popupNoticeLayer = document.getElementById("popup_layer");

/* 알림창 내용 */
const popupNoticeContent = document.querySelector(".popup_content > p");


// input type="hidden" 테그에 
// deleteSet에 저장된 값을 "1,2,3" 형태로 변경해서 저장

// Array.from(deleteSet) : Set -> Array 변경

// JS 배열은 string에 대입되거나 출력될때 
// 요소, 요소, 요소 형태의 문자열을 반환한다!

const introFrm = document.getElementById("editIntroFrm");

introFrm.addEventListener("submit", function(e) {

if(libraryName.value.trim().length == 0){
    e.preventDefault();
    openNoticeWindow("도서관 명을 입력해주세요.");
    return;
}

if(addressInput.value.trim().length == 0){
    e.preventDefault();
    openNoticeWindow("도서관 주소를 입력해주세요.");
    return;
}

if(libraryIntroText.value.trim().length == 0){
    e.preventDefault();
    openNoticeWindow("도서관 소개글을 입력해주세요.");
    return;
}

if(curLat.value.trim().length == 0 ){
    e.preventDefault();
    openNoticeWindow("지도 검색해 주세요.");
    return;
}

curLat.value = lat;
curLng.value = lng;

document.querySelector("[name='deleteList']").value = Array.from(deleteSet);

})

if(message != ""){
    openNoticeWindow(message);
}

function openNoticeWindow(message){
    popupNoticeLayer.style.display = "block";
    openNoticeMessage(message);
}

function openNoticeMessage(message){
    popupNoticeContent.innerHTML = message;
    document.getElementById("confirm_btn").setAttribute("onClick", "closeNoticeMessage()");
}

function closeNoticeMessage(){
    popupNoticeLayer.style.display = "none";
}