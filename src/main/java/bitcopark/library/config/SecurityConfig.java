package bitcopark.library.config;

import bitcopark.library.jwt.JwtFilter;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.repository.jwt.RefreshTokenBlackListRepository;
import bitcopark.library.oauth2.*;
import bitcopark.library.repository.jwt.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("local")
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailHandler customLoginFailHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.addAllowedOrigin("https://bitlibrary.kro.kr");
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*");
            configuration.setAllowCredentials(true);
            return configuration;
        };
    }

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

//        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));
//
//
//        http.requiresChannel(channel ->
//                        channel.anyRequest().requiresSecure()
//        );

        return http.build();
    }

}
