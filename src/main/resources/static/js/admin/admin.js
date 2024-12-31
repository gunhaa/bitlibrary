const side = document.querySelectorAll(".side-wrap > a");
const params = new URL(location.href).searchParams;

for(let i = 0; i < side.length; i++){
    if(side[i].getAttribute("href") == location.pathname){
        side[i].style.fontWeight = "bold";
    }
}

const updateSet = new Set();
const all = document.getElementById("all");
const checkBox = document.querySelectorAll("td > input[type='checkbox']");

if(all != null){
	all.addEventListener("click", () => {
        for(let i=0; i<checkBox.length; i++){
            const no = checkBox[i].nextElementSibling.value;
            if(all.checked){
                updateSet.add(no);
            }else{
                updateSet.delete(no);
            }
            checkBox[i].checked = all.checked;
        }
    })
}


for(let i=0; i<checkBox.length; i++){
    checkBox[i].addEventListener("change", e=>{
        const no = e.target.nextElementSibling.value;
        if(checkBox[i].checked){
            updateSet.add(no);

        }else{
            updateSet.delete(no);
        }
    })
}


const adminFrm = document.querySelector("[name='adminFrm']");

if(adminFrm != null){
    adminFrm.addEventListener("submit", e=>{

        const updateList = document.querySelector("[name='updateList']");
        updateList.value = Array.from(updateSet);

        if(updateList.value.trim().length == 0){
            alert("선택 후 눌러주세요");
            e.preventDefault();
        }
    })
}