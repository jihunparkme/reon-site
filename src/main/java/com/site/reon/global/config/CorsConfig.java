package com.site.reon.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 사용자 자격 증명 지원 여부
        config.addAllowedOriginPattern("*"); // CORS 허용 목록
        config.addAllowedHeader("*"); // CORS 허용 헤더
        config.addAllowedMethod("*"); // CORS 허용 메소드

        source.registerCorsConfiguration("/**", config); // CORS 구성 매핑 설정

        return new CorsFilter(source);
    }
}