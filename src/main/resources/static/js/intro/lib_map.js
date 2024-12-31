	const mapBtn = document.getElementsByClassName("map-btn");
	
  const realMap = document.getElementsByClassName("real-map");

  for(let i = 0; i < mapBtn.length; i++){

      mapBtn[i].addEventListener("click",e=>{
          for(let j = 0; j < mapBtn.length; j++){
              mapBtn[j].classList.remove("current");
              realMap[j].classList.remove("current");
          }

          e.target.classList.toggle("current");
          realMap[i].classList.toggle("current");
      })
  }