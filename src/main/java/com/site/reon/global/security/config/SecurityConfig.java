package com.site.reon.global.security.config;

import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.handler.CustomLoginSuccessHandler;
import com.site.reon.global.security.jwt.CustomAccessDeniedHandler;
import com.site.reon.global.security.jwt.CustomAuthenticationEntryPoint;
import com.site.reon.global.security.jwt.CustomSecurityConfigurerAdapter;
import com.site.reon.global.security.oauth2.handler.OAuth2SuccessHandler;
import com.site.reon.global.security.oauth2.handler.Oauth2FailureHandler;
import com.site.reon.global.security.oauth2.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("!test & !dev")
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
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
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/img/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/vendor/**"),
                                new AntPathRequestMatcher("/favicon.ico"),
                                new AntPathRequestMatcher("/error"),

                                new AntPathRequestMatcher("/management/actuator/health"),
                                new AntPathRequestMatcher("/profile"),
                                new AntPathRequestMatcher("/workshop"),

                                new AntPathRequestMatcher("/contact/**"),
                                new AntPathRequestMatcher("/login/**"),
                                new AntPathRequestMatcher("/member/**"),
                                new AntPathRequestMatcher("/record/**"),
                                new AntPathRequestMatcher("/api/**")
                        ).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/admin/**"),
                                new AntPathRequestMatcher("/management/actuator/**")
                        ).hasAuthority(Role.ADMIN.key())
                        .anyRequest().authenticated()
                )

                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
                        .successHandler(successHandler()))

                .logout((logout) ->
                        logout.invalidateHttpSession(true)
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                )

                .oauth2Login(oauth2 -> // oauth2 로그인 기능 설정
                        oauth2.userInfoEndpoint(userInfo -> // oauth2 로그인 성공 이후 사용자 정보 조회 설정
                                        userInfo.userService(customOauth2UserService)) // 사용자 정보 조회 이후 기능
                                .successHandler(oauth2SuccessHandler)
                                .failureHandler(oauth2FailureHandler))

                .with(new CustomSecurityConfigurerAdapter(), customizer -> {});

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/");
    }
}
