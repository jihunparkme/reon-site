package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.command.domain.Authority;
import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.infra.service.MemberEmailAuthCodeService;
import com.site.reon.aggregate.member.service.dto.AppleOAuth2Token;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final MemberEmailAuthCodeService memberEmailAuthCodeService;

    @Override
    @Transactional
    public void signUpWithEmail(final SignUpDto signUpDto) {
        memberEmailAuthCodeService.checkEmailVerificationStatus(KeyPrefix.SIGN_UP, signUpDto.getEmail());
        validateEmailAndOAuthClient(signUpDto.getEmail(), OAuth2Client.EMPTY);

        final Authority authority = Authority.builder()
                .authorityName(Role.USER.key())
                .build();

        final Member member = Member.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .roasterSn(signUpDto.getRoasterSn())
                .authorities(Collections.singleton(authority))
                .oAuthClient(OAuth2Client.EMPTY)
                .activated(true)
                .build();

        memberRepository.save(member);
    }

    @Override
    public boolean verifySocialEmail(final ApiEmailVerifyRequest request) {
        final String authClientName = request.getAuthClientName().toLowerCase();
        OAuth2Client.validateClientName(authClientName);

        final OAuth2Client oAuth2Client = OAuth2Client.of(authClientName);
        if (OAuth2Client.APPLE == oAuth2Client) {
            return verifyAppleEmail(request.getToken(), oAuth2Client);
        }

        return verifyKakaoAndGoogleEmail(request.getEmail(), oAuth2Client);
    }

    @Override
    public void emailAuthenticate(final LoginDto loginDto) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean verifyAppleEmail(final String token, final OAuth2Client oAuth2Client) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Token is required for Apple login.");
        }

        final AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(token);
        final String email = appleOAuth2Token.getEmail();
        if (StringUtils.isEmpty(email)) {
            log.warn("The token information is invalid. token: {}", token);
            throw new IllegalArgumentException("The token information is invalid.");
        }

        return isExistMember(email, oAuth2Client);
    }

    private boolean verifyKakaoAndGoogleEmail(final String email, final OAuth2Client oAuth2Client) {
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("Email is required for Kakao, Google login.");
        }

        return isExistMember(email, oAuth2Client);
    }

    private void validateEmailAndOAuthClient(final String email, final OAuth2Client oAuthClient) {
        final Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            throw new DuplicateMemberException();
        }
    }

    private boolean isExistMember(final String email, final OAuth2Client oAuthClient) {
        final Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            return true;
        }

        return false;
    }
}
