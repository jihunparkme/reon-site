package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.ApiEmailVerifyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberLoginServiceImplUnitTest {

    private MemberRepository memberRepository = mock(MemberRepository.class);
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private AuthenticationManagerBuilder authenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);
    private MemberLoginService memberLoginService = new MemberLoginServiceImpl(memberRepository, passwordEncoder, authenticationManagerBuilder);

    @Test
    void verify_email_of_email_is_null() throws Exception {
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .clientName("KAKAO")
                .email(null)
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifyEmail(verifyDto)
        );

        assertEquals("Kakao, Google 로그인 시 이메일 정보는 필수값입니다.", exception.getMessage());
    }

    @Test
    void verify_email_of_email_is_empty() throws Exception {
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .clientName("KAKAO")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifyEmail(verifyDto)
        );

        assertEquals("Kakao, Google 로그인 시 이메일 정보는 필수값입니다.", exception.getMessage());
    }

    @Test
    void verify_email_of_kakao_login_should_be_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .clientName("KAKAO")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmail(email))
                .willReturn(Optional.of(Member.builder()
                        .email(email)
                        .build()));

        boolean result = memberLoginService.verifyEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void verify_email_of_kakao_login_should_be_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .clientName("kakao")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmail(email))
                .willReturn(Optional.empty());

        boolean result = memberLoginService.verifyEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void verify_email_of_google_login_should_be_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .clientName("google")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmail(email))
                .willReturn(Optional.of(Member.builder()
                        .email(email)
                        .build()));

        boolean result = memberLoginService.verifyEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void verify_email_of_google_login_should_be_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .clientName("google")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmail(email))
                .willReturn(Optional.empty());

        boolean result = memberLoginService.verifyEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void verify_email_of_apple_login_should_be_return_true() throws Exception {

    }

    @Test
    void verify_email_of_apple_login_should_be_return_false() throws Exception {

    }
}