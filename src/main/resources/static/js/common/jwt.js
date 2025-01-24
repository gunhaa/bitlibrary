

document.addEventListener("DOMContentLoaded", ()=> {
    console.log("jwt connect");

    const getCookie = name => {
        const cookieArr = document.cookie.split(";");
        for (let i = 0; i < cookieArr.length; i++) {
            let cookie = cookieArr[i].trim();
            if (cookie.startsWith(name + "=")) {
                return cookie.substring(name.length + 1);
            }
        }
        return null;
    }

    const parseJWT = token => {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map( c => {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    }

    const accessToken = getCookie("access");
    if(accessToken){
        const jsonPayload = parseJWT(accessToken);
        const loginArea = document.querySelector(".main-login-area");

        // 로그인 정보 얻어오기 post 필요

        loginArea.innerHTML=`
                                   <article class="main-login-title main-login-member-title">
                                     <strong>${jsonPayload.username.split(" ")[2]}</strong>님 환영합니다.
                                   </article>

                                   <article class="main-login-form main-login-table">
                                     <table>
                                       <tr>
                                         <td>도서대출현황</td>
                                         <td>3</td>
                                       </tr>
                                       <tr class="second-tr">
                                         <td>도서예약현황</td>
                                         <td>4</td>
                                       </tr>
                                       <tr>
                                         <td>연체도서</td>
                                         <td>5</td>
                                       </tr>
                                     </table>
                                     <div class="main-login-btn-area main-logout-btn-area">
                                       <div id="main-my-library-btn"><a href="/OAuth2/logout">로그아웃</a></div>
                                       <div id="main-my-library-btn"><a href="/myLibrary/book">내 서재</a></div>
                                     </div>
                                   </article>
                                 `;
    }

});

