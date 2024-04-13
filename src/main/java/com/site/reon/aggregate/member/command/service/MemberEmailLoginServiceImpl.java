package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.domain.Authority;
import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.infra.service.MemberEmailAuthCodeService;
import com.site.reon.aggregate.member.command.dto.LoginRequest;
import com.site.reon.aggregate.member.command.dto.SignUpRequest;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.security.exception.DuplicateMemberException;
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
public class MemberEmailLoginServiceImpl implements MemberEmailLoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberEmailAuthCodeService memberEmailAuthCodeService;

    @Override
    @Transactional
    public void signUpWithEmail(final SignUpRequest signUpRequest) {
        memberEmailAuthCodeService.checkEmailVerificationStatus(KeyPrefix.SIGN_UP, signUpRequest.getEmail());
        validateEmailAndOAuthClient(signUpRequest.getEmail(), OAuth2Client.EMPTY);

        final Authority authority = Authority.builder()
                .authorityName(Role.USER.key())
                .build();

        final Member member = Member.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roasterSn(signUpRequest.getRoasterSn())
                .authorities(Collections.singleton(authority))
                .oAuthClient(OAuth2Client.EMPTY)
                .activated(true)
                .build();

        memberRepository.save(member);
    }

    @Override
    public void emailAuthenticate(final LoginRequest loginRequest) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void validateEmailAndOAuthClient(final String email, final OAuth2Client oAuthClient) {
        final Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            throw new DuplicateMemberException();
        }
    }
}
