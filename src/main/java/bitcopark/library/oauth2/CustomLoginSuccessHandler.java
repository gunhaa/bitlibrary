package bitcopark.library.oauth2;

import bitcopark.library.entity.jwt.RefreshToken;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.repository.jwt.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Profile("local")
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String username = customOAuth2User.getUsername();
        String email = customOAuth2User.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String access = jwtUtil.createJwt("access", username, email, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, email, role, 86400000L);

        saveRefreshToken(username, email, refresh, 86400000L);

        ResponseCookie accessCookie = ResponseCookie.from("access", access).path("/").maxAge(600).sameSite("None").secure(true).build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh", refresh).path("/").maxAge(24 * 60 * 60).sameSite("None").secure(true).httpOnly(true).build();
        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
        response.setStatus(HttpStatus.OK.value());
//        System.out.println("accessCookie = " + accessCookie);
//        System.out.println("로그인 성공 후 CustomLoginHandler에서 쿠키 부여 완료");
        response.sendRedirect("/");

    }

    private Cookie createAccessCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(600);
        cookie.setPath("/");
        return cookie;
    }

    private Cookie createRefreshCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void saveRefreshToken(String username, String email, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = RefreshToken.createRefreshToken(username, email, refresh, date.toString());

        refreshRepository.save(refreshToken);
    }


}
