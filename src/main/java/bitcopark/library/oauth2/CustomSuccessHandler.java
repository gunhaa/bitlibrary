package bitcopark.library.oauth2;

import bitcopark.library.entity.jwt.RefreshToken;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.repository.jwt.RefreshRepository;
import bitcopark.library.repository.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;

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

        // 로그인 성공시 가입이 안된 이메일이라면 가입시킨다.
        boolean isExist = memberRepository.existsByEmail(email);

        if(!isExist){
            Member oAuth2Member = Member.createOAuth2Member(email, username);
            memberRepository.save(oAuth2Member);
        }

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());

        response.sendRedirect("http://localhost:8080/");

    }

    private Cookie createCookie(String key, String value) {
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
