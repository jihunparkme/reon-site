package com.site.reon.global.security.jwt;

import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.security.dto.SessionMember;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            SessionMember member = (SessionMember) session.getAttribute(SessionConst.LOGIN_MEMBER);
            if (member != null) {
                Collection<? extends GrantedAuthority> authorities = member.getAuthorityDtoSet().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                        .collect(Collectors.toList());

                User principal = new User(member.getName(), member.getEmail(), authorities);

                Authentication auth = new UsernamePasswordAuthenticationToken(principal, member.getEmail(), authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.debug("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", auth.getName(), requestURI);
            } else {
                log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}