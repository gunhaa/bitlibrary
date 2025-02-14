package bitcopark.library.jwt;

import bitcopark.library.entity.jwt.RefreshToken;
import bitcopark.library.entity.jwt.RefreshTokenBlackList;
import bitcopark.library.repository.jwt.RefreshRepository;
import bitcopark.library.repository.jwt.RefreshTokenBlackListRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Deprecated
@Profile("local")
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Transactional
    public ResponseEntity<?> verifyAndReissueRefreshToken(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("refresh")){
                    refreshToken=cookie.getValue();
                }
            }
        }

        if(refreshToken==null){
            return new ResponseEntity<>("refreshToken null", HttpStatus.BAD_REQUEST);
        }

        try{
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>("refreshToken expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refreshToken);
        if(!category.equals("refreshToken")){
            return new ResponseEntity<>("invalid refreshToken", HttpStatus.BAD_REQUEST);
        }


        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefreshToken(refreshToken);
        if (!isExist) {

            //response body
            return new ResponseEntity<>("invalid refreshToken", HttpStatus.BAD_REQUEST);
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

        // 스케줄 작업을 통해 만료시간이 지난 토큰은 주기적으로 삭제하는 것을 추가 구현해야한다


        response.addCookie(createCookie("refresh", newRefresh));
        response.setHeader("access", newAccess);

        return new ResponseEntity<>("refreshToken token issued",HttpStatus.OK);
    }




    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void saveRefreshToken(String username, String refresh, String email, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = RefreshToken.createRefreshToken(username, email, refresh, date.toString());

        refreshRepository.save(refreshToken);
    }

}
