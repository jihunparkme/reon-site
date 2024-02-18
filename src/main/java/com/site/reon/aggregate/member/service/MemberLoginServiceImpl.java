package com.site.reon.aggregate.member.service;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiWithdrawRequest;
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
        validateEmailAndOAuthClient(signUpDto.getEmail(), OAuth2Client.EMPTY);

        Authority authority = Authority.builder()
                .authorityName(Role.USER.key())
                .build();

        Member member = Member.builder()
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
    public boolean verifySocialEmail(ApiEmailVerifyRequest request) {
        String authClientName = request.getAuthClientName().toLowerCase();
        OAuth2Client.validateClientName(authClientName);

        OAuth2Client oAuth2Client = OAuth2Client.of(authClientName);
        if (OAuth2Client.APPLE == oAuth2Client) {
            return verifyAppleEmail(request.getToken(), oAuth2Client);
        }

        return verifyKakaoAndGoogleEmail(request.getEmail(), oAuth2Client);
    }

    @Override
    public void emailAuthenticate(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public MemberDto oAuth2SignUp(ApiOAuth2SignUpRequest request) {
        String authClientName = request.getAuthClientName().toLowerCase();
        OAuth2Client.validateClientName(authClientName);
        validateEmailAndOAuthClient(request.getEmail(), OAuth2Client.of(authClientName));

        Member member = request.toMember();
        return MemberDto.from(memberRepository.save(member));
    }

    @Override
    @Transactional
    public boolean withdraw(ApiWithdrawRequest request) {
        String email = request.getEmail();
        String authClientName = request.getAuthClientName().toLowerCase();
        if (StringUtils.isBlank(authClientName)) {
            return memberDeleteResult(email, OAuth2Client.EMPTY);
        }

        OAuth2Client.validateClientName(authClientName);
        return memberDeleteResult(email, OAuth2Client.of(authClientName));
    }

    private boolean memberDeleteResult(String email, OAuth2Client oAuth2Client) {
        boolean existMember = isExistMember(email, oAuth2Client);
        if (!existMember) {
            throw new IllegalArgumentException("No registered member information.");
        }

        int deleteCount = memberRepository.deleteByEmailAndOAuthClient(email, oAuth2Client);
        if (deleteCount > 0) {
            return true;
        }

        throw new IllegalArgumentException("Failed to delete member.");
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

        return isExistMember(email, oAuth2Client);
    }

    private boolean verifyKakaoAndGoogleEmail(String email, OAuth2Client oAuth2Client) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email is required for Kakao, Google login.");
        }

        return isExistMember(email, oAuth2Client);
    }

    private void validateEmailAndOAuthClient(String email, OAuth2Client oAuthClient) {
        Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            throw new DuplicateMemberException("이미 가입되어 있는 이메일입니다.");
        }
    }

    private boolean isExistMember(String email, OAuth2Client oAuthClient) {
        Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            return true;
        }

        return false;
    }
}
