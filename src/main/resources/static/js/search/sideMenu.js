const path = location.pathname;
const sideMenuArticle = document.getElementsByClassName("side-menu-article")[0];

if(path.startsWith("/book/1/1")){
    sideMenuArticle.innerHTML = `
    <div class="category">자료 검색</div>
    <ul class="side-menu">
        <li class="side-current">
            <a href="/book/1/1" class="side-current-a">통합 자료 검색</a>
            <ul class="side-side-current">
                <li><a href="/book/1/1"  class="side-side-current">간략검색</a></li>
                <li><a href="/book/1/2">상세검색</a></li>
            </ul>
        </li>
        <li class="last-menu"><a href="/book/2">희망 도서 신청</a></li>
    </ul>
    `;
} else if(path.startsWith("/book/1/2")){
    sideMenuArticle.innerHTML = `
    <div class="category">자료 검색</div>
    <ul class="side-menu">
        <li class="side-current">
            <a href="/book/1/1" class="side-current-a">통합 자료 검색</a>
            <ul class="side-side-current">
                <li><a href="/book/1/1">간략검색</a></li>
                <li><a href="/book/1/2" class="side-side-current">상세검색</a></li>
            </ul>
        </li>
        <li class="last-menu"><a href="/book/2">희망 도서 신청</a></li>
    </ul>
    `;      
}else if(path.startsWith("/book/2")){
    sideMenuArticle.innerHTML = `
    <div class="category">자료 검색</div>
    <ul class="side-menu">
        <li><a href="/book/1/1">통합 자료 검색</a></li>
        <li class="last-menu side-current"><a href="/book/2" class="side-current-a">희망 도서 신청</a></li>
    </ul>
    `;
}
 