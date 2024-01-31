package com.site.reon.aggregate.member.service;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.ApiEmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.AppleOAuth2Token;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
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
    public boolean verifyEmail(ApiEmailVerifyDto emailVerityDto) {
        OAuth2Client oAuth2Client = OAuth2Client.of(emailVerityDto.getOAuth2ClientName().toLowerCase());
        if (OAuth2Client.APPLE == oAuth2Client) {
            return verifyAppleEmail(emailVerityDto.getToken());
        }

        return verifyKakaoAndGoogleEmail(emailVerityDto.getEmail());
    }

    @Override
    public void emailAuthenticate(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean verifyAppleEmail(String token) {
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("Token is required for Apple login.");
        }

        AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(token);
        String email = appleOAuth2Token.getEmail();
        if (StringUtils.isBlank(email)) {
            log.warn("The token information is invalid. token: {}", token);
            throw new IllegalArgumentException("The token information is invalid.");
        }

        return getOAuth2EmailMember(email);
    }

    private boolean verifyKakaoAndGoogleEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email is required for Kakao, Google login.");
        }

        return getOAuth2EmailMember(email);
    }

    private boolean getOAuth2EmailMember(String email) {
        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isPresent()) {
            return true;
        }

        return false;
    }
}
