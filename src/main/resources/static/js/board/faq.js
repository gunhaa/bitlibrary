$("div").on("click", function(){

    if($(this).next("p").css("display")== "none"){

        $(this).siblings(".faq-answer").slideUp();
        $(this).next().slideDown();

    }else{
        $(this).next().slideUp();
    }
})

const li = document.querySelectorAll(".side-menu > li");

switch(cateCode){
    case "15" : li[0].classList.add("side-current"); break;
    case "16" : li[1].classList.add("side-current"); break;
    case "17" : li[2].classList.add("side-current"); break;
    case "18" : li[3].classList.add("side-current"); break;
} 

   