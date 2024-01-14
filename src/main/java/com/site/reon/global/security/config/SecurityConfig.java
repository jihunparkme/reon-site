package com.site.reon.global.security.config;

import com.site.reon.global.common.constant.member.AuthConst;
import com.site.reon.global.security.jwt.JwtAccessDeniedHandler;
import com.site.reon.global.security.jwt.JwtAuthenticationEntryPoint;
import com.site.reon.global.security.jwt.JwtSecurityConfig;
import com.site.reon.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
@Profile("!test & !dev")
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserDetailsService userDetailsService;

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
                        ).hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
                        .successForwardUrl("/"))

                .logout((logout) ->
                        logout.deleteCookies(AuthConst.ACCESS_TOKEN)
                                .invalidateHttpSession(false)
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                )

                .sessionManagement(sessionManagement -> // 세션 미사용 설정
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
