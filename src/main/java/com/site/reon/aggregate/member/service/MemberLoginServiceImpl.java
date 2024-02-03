package com.site.reon.aggregate.member.service;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.*;
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
        Member findMember = memberRepository.findWithAuthoritiesByEmailAndOAuthClient(signUpDto.getEmail(), OAuth2Client.EMPTY)
                .orElse(null);

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
                .oAuthClient(OAuth2Client.EMPTY)
                .activated(true)
                .build();

        memberRepository.save(member);
    }

    @Override
    public boolean verifyEmail(ApiEmailVerifyDto emailVerityDto) {
        String authClientName = emailVerityDto.getAuthClientName().toLowerCase();
        OAuth2Client.validateClientName(authClientName);

        OAuth2Client oAuth2Client = OAuth2Client.of(authClientName);
        if (OAuth2Client.APPLE == oAuth2Client) {
            return verifyAppleEmail(emailVerityDto.getToken(), oAuth2Client);
        }

        return verifyKakaoAndGoogleEmail(emailVerityDto.getEmail(), oAuth2Client);
    }

    @Override
    public void emailAuthenticate(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public MemberDto oAuth2SignUp(ApiOAuth2SignUp apiOAuth2SignUp) {
        OAuth2Client.validateClientName(apiOAuth2SignUp.getAuthClientName().toLowerCase());

        Member member = apiOAuth2SignUp.toMember();
        return MemberDto.from(memberRepository.save(member));
    }

    private boolean verifyAppleEmail(String token, OAuth2Client oAuth2Client) {
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("Token is required for Apple login.");
        }

        AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(token);
        String email = appleOAuth2Token.getEmail();
        if (StringUtils.isBlank(email)) {
            log.warn("The token information is invalid. token: {}", token);
            throw new IllegalArgumentException("The token information is invalid.");
        }

        return getOAuth2EmailMember(email, oAuth2Client);
    }

    private boolean verifyKakaoAndGoogleEmail(String email, OAuth2Client oAuth2Client) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email is required for Kakao, Google login.");
        }

        return getOAuth2EmailMember(email, oAuth2Client);
    }

    private boolean getOAuth2EmailMember(String email, OAuth2Client oAuthClient) {
        Optional<Member> memberOpt = memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            return true;
        }

        return false;
    }
}
