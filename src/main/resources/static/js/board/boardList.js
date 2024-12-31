const writeBtn = document.getElementById("writeBtn");

if(writeBtn != null){

    // 글쓰기 버튼 클릭 
    writeBtn.addEventListener("click", ()=>{
        
                        // /board2/15(or 16 or 18)/insert
        location.href = `/board2/${cateCode}/insert`;
        
    })
}


// 검색창 검색 기록 유지
const boardSearch = document.querySelector("#boardSearch");
const searchKey = document.querySelector("#searchKey");
const searchQuery = document.querySelector("#searchQuery");
const options = document.querySelectorAll("#searchKey > option");

(()=>{
    const params = new URL(location.href).searchParams;

    const keyword = params.get("keyword"); // t / c / tc / w 중 한 개
    const query = params.get("query"); // 검색어

    // 검색어 입력 시
    if(keyword != null){
        searchQuery.value = query; 

        for(let op of options){
            if(op.value == keyword){
                op.selected = true;
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