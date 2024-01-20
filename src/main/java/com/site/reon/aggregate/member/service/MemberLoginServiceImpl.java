package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.common.constant.member.AuthConst;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    @Transactional
    public void signup(SignUpDto signUpDto) {
        Member findMember = memberRepository.findOneWithAuthoritiesByEmail(signUpDto.getEmail()).orElse(null);
        if (findMember != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 이메일입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName(Role.USER.key())
                .build();

        Member member = Member.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        memberRepository.save(member);
    }

    @Override
    public ResponseCookie getCookie(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginDto.getEmail());

        return ResponseCookie.from(AuthConst.ACCESS_TOKEN.key(), jwt)
                .httpOnly(true)
                .secure(true)
                .sameSite(AuthConst.NONE.key())
                .path("/")
                .build();
    }
}
