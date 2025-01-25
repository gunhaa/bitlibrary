package bitcopark.library.jwt;

import bitcopark.library.entity.jwt.RefreshTokenBlackList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final ReissueService reissueService;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @GetMapping("/OAuth2/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken != null) {
            RefreshTokenBlackList refreshTokenBlackList = RefreshTokenBlackList.createRefreshTokenBlackList(refreshToken);
            refreshTokenBlackListRepository.save(refreshTokenBlackList);
        }


        Cookie accessTokenCookie = new Cookie("access", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/";
    }


    @PostMapping("/OAuth2/reissue")
    @ResponseBody
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
        return reissueService.verifyAndReissueRefreshToken(request, response);
    }
}
