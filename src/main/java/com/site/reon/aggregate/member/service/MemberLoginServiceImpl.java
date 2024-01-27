package com.site.reon.aggregate.member.service;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.EmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.SocialLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

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
    public boolean verifyEmail(EmailVerifyDto emailVerityDto) {
        SocialLogin socialLogin = SocialLogin.of(emailVerityDto.getClientName().toLowerCase());
        if (SocialLogin.APPLE == socialLogin) {
            // 토큰 분리해서 이메일 확인

            return true;
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

    private boolean verifyKakaoAndGoogleEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Kakao, Google 로그인 시 이메일 정보는 필수값입니다.");
        }

        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isPresent()) {
            return true;
        }

        return false;
    }
}
