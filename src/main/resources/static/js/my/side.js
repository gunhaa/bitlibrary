const sideMenu = document.querySelectorAll(".side-menu li > a")
const sideLow = document.querySelectorAll(".side-wrap > a");
const params = new URL(location.href).searchParams;

for(let i = 0; i < sideMenu.length; i++){
    if(sideMenu[i].getAttribute("href").split("/")[1] == location.pathname.split("/")[1]){
        sideMenu[i].parentElement.classList.add("select-menu");
    }
}

for(let i = 0; i < sideLow.length; i++){
    if(sideLow[i].getAttribute("href") == location.pathname){
        sideLow[i].style.fontWeight = "bold";
    }
}