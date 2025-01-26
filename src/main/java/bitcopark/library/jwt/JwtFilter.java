package bitcopark.library.jwt;

import bitcopark.library.entity.jwt.RefreshToken;
import bitcopark.library.entity.jwt.RefreshTokenBlackList;
import bitcopark.library.entity.member.Member;
import bitcopark.library.oauth2.CustomOAuth2User;
import bitcopark.library.repository.jwt.RefreshRepository;
import bitcopark.library.repository.member.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. access 토큰 확인
        // 2. 있고 유효하다면 loginMember 변수에 객체를 레포지토리에서 조회해서 넣는다
        // 3. 있는데 유효하지 않다면 refresh토큰을 통해 reissue를 시도한다
        // 4. 없다면 loginMember에는 아무 값이 들어가지 않는다.
        // 모든 경우 유효하지 않다면 다음 필터로 넘긴다
        // 유효한 경우 loginMember를 부여한다.
        /* 시나리오
         처음에 사이트에 들어왔을때, 메인페이지 로그인 검증이 access토큰 체크하고 있으면 로그인member라는 attributes를 뷰템플릿에 준다.
         , access토큰에 문제있으면 refresh토큰을 이용해 재발급해 로그인멤버를 넣어주는데 이 로직이면 리프레시 토큰이 유효한 하루는 로그아웃이 되지 않는다
         문제가 있는 모든 토큰은 제거한다.
        */

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
            if (jwtUtil.verifyJwt(refreshToken) || jwtUtil.getCategory(refreshToken).equals("refresh")) {
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
                    String newRefresh = jwtUtil.createJwt("refreshToken", username, email, role, 86400000L);

                    refreshRepository.deleteByRefreshToken(refreshToken);
                    saveRefreshToken(username, newRefresh, email, 86400000L);

                    RefreshTokenBlackList refreshTokenBlackList = RefreshTokenBlackList.createRefreshTokenBlackList(refreshToken);
                    refreshTokenBlackListRepository.save(refreshTokenBlackList);

                    response.addCookie(createAccessCookie("access", newAccess));
                    response.addCookie(createRefreshCookie("refresh", newRefresh));
                    //로그인 멤버 추가, 세션 부여
                    setLoginMemberAndGetSession(request, response, filterChain, accessToken);
                    
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
        if (jwtUtil.verifyJwt(accessToken) || jwtUtil.getCategory(accessToken).equals("access")) {

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

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/");
    }

}
