package bitcopark.library.config;

import bitcopark.library.jwt.JwtFilter;
import bitcopark.library.jwt.JwtUtil;
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
