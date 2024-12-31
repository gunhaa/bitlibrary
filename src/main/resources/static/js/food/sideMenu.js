const path = location.pathname.split("/")[1];
const sideMenuArticle = document.getElementsByClassName("side-menu-article");
sideMenuArticle[0].innerHTML = "";

switch(path){
    case "food" :{
        /* 카테고리 */
        const category = document.createElement("div");
        category.classList.add("category");
        category.innerText="맛있는 도서관";

        /* ul-sidemenu */
        const sideUl = document.createElement("ul");
        sideUl.classList.add("side-menu");

        /* li-sidemenu */
        const sideLi1 = document.createElement("li");
        
        sideLi1.classList.add("side-current");
        sideA1 = document.createElement("a");
        sideA1.classList.add("side-current-a");
        sideA1.innerText="구내식당";
        sideA1.setAttribute("href", "/food")
        sideLi1.append(sideA1);

        const sideLi2 = document.createElement("li");
        sideLi2.classList.add("last-menu");
        sideA2 = document.createElement("a");
        sideA2.innerText="카페";
        sideA2.setAttribute("href", "/cafe")
        sideLi2.append(sideA2);

        sideUl.append(sideLi1, sideLi2);

        sideMenuArticle[0].append(category, sideUl);
    }
    
    break;

    case "cafe" : {
        /* 카테고리 */
        const category = document.createElement("div");
        category.classList.add("category");
        category.innerText="맛있는 도서관";
 
         /* ul-sidemenu */
        const sideUl = document.createElement("ul");
        sideUl.classList.add("side-menu");
 
        /* li-sidemenu */
        const sideLi1 = document.createElement("li");
       
        sideA1 = document.createElement("a");
        sideA1.innerText="구내식당";
        sideA1.setAttribute("href", "/food")
        sideLi1.append(sideA1);
 
        const sideLi2 = document.createElement("li");
        sideLi2.classList.add("last-menu");
        sideLi2.classList.add("side-current");
        sideA2 = document.createElement("a");
        sideA2.classList.add("side-current-a");
        sideA2.setAttribute("href", "/cafe")
        sideA2.innerText="카페";
        sideLi2.append(sideA2);
 
        sideUl.append(sideLi1, sideLi2);
 
        sideMenuArticle[0].append(category, sideUl);
    }


    /* switch문 쓰실 거면 걍 백틱 쓰세요.. 저처럼 고생하지 마시고...😥 */
}
