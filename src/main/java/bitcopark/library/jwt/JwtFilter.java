package bitcopark.library.jwt;

import bitcopark.library.entity.member.Member;
import bitcopark.library.oauth2.CustomOAuth2User;
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

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

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
        */

        String accessToken = null;
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("refresh")){
                    refreshToken=cookie.getValue();
                } else if(cookie.getName().equals("access")){
                    accessToken=cookie.getValue();
                }
            }
        }

        System.out.println("accessToken = " + accessToken);

        // 없다면 해당 필터는 건너뛴다
        if(accessToken==null){
            filterChain.doFilter(request, response);
            return;
        }



        if(jwtUtil.verifyJwt(accessToken) || jwtUtil.getCategory(accessToken).equals("access")){

            try{
                jwtUtil.isExpired(accessToken);

            } catch(ExpiredJwtException e){

                // 만료되었다면 refresh토큰 로직 점검 후 새로운 토큰 발급로직 추가
                // refresh토큰으로 인한 로직 실패 시 다음 필터로 넘김
                filterChain.doFilter(request, response);
                return;
            }

        } else {
            // 잘못된 접근이므로 로그인 안된 상태로 넘김
            filterChain.doFilter(request, response);
            return;
        }

        // jwt유효성 검증, 만료기간 검증 완료, 로그인 멤버 추가, 세션 부여

        String email = jwtUtil.getEmail(accessToken);
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        System.out.println("email = " + email);
        System.out.println("username = " + username);
        System.out.println("role = " + role);

        String name = username.split(" ")[2];

        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(email, name,role);

        request.setAttribute("loginMember", loginMemberDTO);

        MemberDto memberDto = new MemberDto(email,username,role);
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
