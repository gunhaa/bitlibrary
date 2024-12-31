// 글쓰기
const writeBtn = document.getElementById("writeBtn");

if(writeBtn != null){

    writeBtn.addEventListener("click", () => {

        location.href=`/scheduling/${location.pathname.split("/")[2]}/insert`;

    })
}

// 검색
const boardSearch = document.getElementById("boardSearch");
const searchKey = document.getElementById("searchKey");
const searchQuery = document.getElementById("searchQuery");
const options = document.querySelectorAll("#searchKey > option");

(()=>{
    const params = new URL(location.href).searchParams;

    const keyword = params.get("keyword"); // t, c, tc 
    const query = params.get("query"); 

    if(keyword != null){
        searchQuery.value = query; 

        for(let option of options){
            if(option.value == key){
                option.selected = true;
            }
        }
    }
})();

// 검색어 미입력 시 검색된 경우
boardSearch.addEventListener("submit", e=>{
    if(searchQuery.value.trim().length == 0){ // 검색어 입력 X
        e.preventDefault(); // 기본 이벤트 제거

        location.href = location.pathname; // 게시판 첫번째 페이지로 이동
    }
})