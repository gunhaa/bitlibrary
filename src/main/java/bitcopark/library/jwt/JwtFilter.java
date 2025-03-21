package bitcopark.library.jwt;

import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.dto.MemberDto;
import bitcopark.library.entity.jwt.RefreshToken;
import bitcopark.library.entity.jwt.RefreshTokenBlackList;
import bitcopark.library.oauth2.CustomOAuth2User;
import bitcopark.library.repository.jwt.RefreshRepository;
import bitcopark.library.repository.jwt.RefreshTokenBlackListRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                } else if (cookie.getName().equals("access")) {
                    accessToken = cookie.getValue();
                }
            }
        }

        if (accessToken == null) {

            if (refreshToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // refresh토큰 테스트 후 재발급 로직 실행
            // 문제있는 refresh토큰을 가지고있다면 refresh토큰을 제거하고 다음 필터로 넘긴다.
            if (jwtUtil.verifyJwt(refreshToken) && jwtUtil.getCategory(refreshToken).equals("refresh")) {
                try {

                    // DB 확인
                    Boolean refreshTokenExist = refreshRepository.existsByRefreshToken(refreshToken);
                    if (!refreshTokenExist) {
                        removeRefreshToken(response);
                        filterChain.doFilter(request, response);
                        return;
                    }
                    
                    // 블랙리스트 확인
                    Boolean refreshTokenBlackListExist = refreshTokenBlackListRepository.existsByRefreshToken(refreshToken);
                    if(refreshTokenBlackListExist){
                        removeRefreshToken(response);
                        filterChain.doFilter(request, response);
                        return;
                    }

                    String username = jwtUtil.getUsername(refreshToken);
                    String email = jwtUtil.getEmail(refreshToken);
                    String role = jwtUtil.getRole(refreshToken);

                    String newAccess = jwtUtil.createJwt("access", username, email, role, 600000L);
                    String newRefresh = jwtUtil.createJwt("refresh", username, email, role, 86400000L);

                    refreshRepository.deleteByRefreshToken(refreshToken);
                    saveRefreshToken(username, newRefresh, email, 86400000L);

                    RefreshTokenBlackList refreshTokenBlackList = RefreshTokenBlackList.createRefreshTokenBlackList(refreshToken);
                    refreshTokenBlackListRepository.save(refreshTokenBlackList);

                    ResponseCookie accessCookie = ResponseCookie.from("access", newAccess).path("/").maxAge(600).sameSite("None").secure(true).build();
                    ResponseCookie refreshCookie = ResponseCookie.from("refresh", newRefresh).path("/").maxAge(24 * 60 * 60).sameSite("None").secure(true).httpOnly(true).build();
                    response.addHeader("Set-Cookie", accessCookie.toString());
                    response.addHeader("Set-Cookie", refreshCookie.toString());


                    //로그인 멤버 추가, 세션 부여
                    setLoginMemberAndGetSession(request, response, filterChain, newAccess);
                    filterChain.doFilter(request, response);
                    return;
                    
                }catch(ExpiredJwtException e){
                    removeRefreshToken(response);
                    filterChain.doFilter(request, response);
                    return;
                }

            } else {
                removeRefreshToken(response);
                filterChain.doFilter(request, response);
                return;
            }
            
        }

        // 문제가 있는 access 토큰이라면 그것을 제거한다.
        if (jwtUtil.verifyJwt(accessToken) && jwtUtil.getCategory(accessToken).equals("access")) {

            try {
                // 필요없는 로직일 수 있다
                // jwt만료기간과 쿠키 만료시간이 일치한다.
                jwtUtil.isExpired(accessToken);

            } catch (ExpiredJwtException e) {
                removeAccessToken(response);
                filterChain.doFilter(request, response);
                return;
            }

        } else {
            removeAccessToken(response);
            filterChain.doFilter(request, response);
            return;
        }

        //로그인 멤버 추가, 세션 부여
        setLoginMemberAndGetSession(request, response, filterChain, accessToken);
        filterChain.doFilter(request, response);
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

    private void removeAccessToken(HttpServletResponse response){
        Cookie cookie = new Cookie("access", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void removeRefreshToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void saveRefreshToken(String username, String refresh, String email, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = RefreshToken.createRefreshToken(username, email, refresh, date.toString());

        refreshRepository.save(refreshToken);
    }
    
    private void setLoginMemberAndGetSession(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String accessToken) throws IOException, ServletException {
        String email = jwtUtil.getEmail(accessToken);
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        String name = username.split(" ")[2];

        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(email, name, role);

        request.setAttribute("loginMember", loginMemberDTO);

        MemberDto memberDto = new MemberDto(email, username, role);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/");
    }

}
