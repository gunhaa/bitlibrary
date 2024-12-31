function getRandomColor() {
    let red = Math.floor(Math.random() * 256);
    let green = Math.floor(Math.random() * 256);
    let blue = Math.floor(Math.random() * 256);
    return `rgb(${red}, ${green}, ${blue})`;
}
 

const mainLoginBtn = document.getElementById("main-login-btn");

if(mainLoginBtn != null){
    setInterval(() => mainLoginBtn.style.backgroundColor=getRandomColor(), 140);
}
