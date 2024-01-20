package com.site.reon.global.security.config;

import com.site.reon.global.common.constant.member.AuthConst;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.jwt.JwtAccessDeniedHandler;
import com.site.reon.global.security.jwt.JwtAuthenticationEntryPoint;
import com.site.reon.global.security.jwt.JwtSecurityConfig;
import com.site.reon.global.security.jwt.TokenProvider;
import com.site.reon.global.security.oauth2.config.OAuth2SuccessHandler;
import com.site.reon.global.security.oauth2.config.Oauth2FailureHandler;
import com.site.reon.global.security.oauth2.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("test | dev")
public class DevSecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserDetailsService userDetailsService;
    private final CustomOauth2UserService customOauth2UserService;
    private final OAuth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2FailureHandler oauth2FailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .csrf(AbstractHttpConfigurer::disable) // token 을 사용하므로 csrf disable

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/img/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/vendor/**"),

                                new AntPathRequestMatcher("/profile"),
                                new AntPathRequestMatcher("/management/actuator/health"),

                                new AntPathRequestMatcher("/login/**"),
                                new AntPathRequestMatcher("/member/**"),
                                new AntPathRequestMatcher("/record/**")
                        ).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/admin/**"),
                                new AntPathRequestMatcher("/management/actuator/**")
                        ).hasAuthority(Role.ADMIN.name())
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
                        .successForwardUrl("/"))

                .logout((logout) ->
                        logout.deleteCookies(AuthConst.ACCESS_TOKEN.key())
                                .invalidateHttpSession(false)
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                )

                .oauth2Login(oauth2 -> // oauth2 로그인 기은 설정
                        oauth2.userInfoEndpoint(userInfo -> // oauth2 로그인 성공 이후 사용자 정보 조회 설정
                                userInfo.userService(customOauth2UserService)) // 사용자 정보 조회 이후 기능
                                .successHandler(oauth2SuccessHandler)
                                .failureHandler(oauth2FailureHandler))

                .sessionManagement(sessionManagement -> // 세션 미사용 설정
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .headers(headers -> // h2 console 사용을 위한 설정
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .with(new JwtSecurityConfig(tokenProvider), customizer -> {});

        return http.build();
    }
}
