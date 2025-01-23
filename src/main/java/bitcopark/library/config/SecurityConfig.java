package bitcopark.library.config;

import bitcopark.library.jwt.JwtFilter;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.oauth2.CustomFailHandler;
import bitcopark.library.oauth2.CustomLogoutFilter;
import bitcopark.library.oauth2.CustomOAuth2UserService;
import bitcopark.library.oauth2.CustomSuccessHandler;
import bitcopark.library.repository.jwt.RefreshRepository;
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
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailHandler customFailHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshRepository refreshRepository;
    private final CustomLogoutFilter customLogoutFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((auth)-> auth.disable());

        http.formLogin((auth)->auth.disable());

        http.httpBasic((auth)->auth.disable());

        http.authorizeHttpRequests((auth)->
                        auth.requestMatchers("/study").hasAuthority("ROLE_USER")
                                .anyRequest().permitAll()
                );

        http.addFilterBefore(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);

        http.sessionManagement((session)->{
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.oauth2Login((oauth2) ->
                oauth2.loginPage("/login")
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailHandler)
                );


        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        return http.build();
    }

}
