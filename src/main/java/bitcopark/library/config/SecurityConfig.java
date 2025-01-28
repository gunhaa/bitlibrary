package bitcopark.library.config;

import bitcopark.library.jwt.JwtFilter;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.jwt.RefreshTokenBlackListRepository;
import bitcopark.library.oauth2.CustomLoginFailHandler;
import bitcopark.library.oauth2.CustomLogoutFilter;
import bitcopark.library.oauth2.CustomOAuth2UserService;
import bitcopark.library.oauth2.CustomLoginSuccessHandler;
import bitcopark.library.repository.jwt.RefreshRepository;
import bitcopark.library.repository.member.MemberRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailHandler customLoginFailHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((auth)-> auth.disable());

        http.formLogin((auth)->auth.disable());

        http.httpBasic((auth)->auth.disable());

        http.authorizeHttpRequests((auth)->
                        auth.requestMatchers("/study/**").authenticated()
                                .requestMatchers("/api/study/**").authenticated()
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                .anyRequest().permitAll()
                );

        http.addFilterBefore(new JwtFilter(jwtUtil, refreshRepository, refreshTokenBlackListRepository), OAuth2LoginAuthenticationFilter.class);

        http.sessionManagement((session)->{
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.oauth2Login((oauth2) ->
                oauth2.loginPage("/")
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(customLoginSuccessHandler)
                        .failureHandler(customLoginFailHandler)
                );


        //http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        return http.build();
    }

}
