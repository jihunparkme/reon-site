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
    private final static String APPLE_LOGIN_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYWFyb24ucGFyayIsImV4cCI6MTcwNjQ0NTUyOSwiaWF0IjoxNzA2MzU5MTI5LCJzdWIiOiIwMDAzODUuMDQ3c2dmNjZhYnM2NGQ2MGE0MDZkNWQ0YjNiNHgydjIuMTk5MyIsImNfaGFzaCI6IkYyWWRiN0R2RUJZaE9vUElHdGhEb0ciLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNzA2MzU5MTI5LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.8DWNWY3PkDRdXzAjmrcaWH9p0tvjmg3ieOH4MZXz7Gs";

    @Test
    void verify_email_of_email_is_null() throws Exception {
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .oAuth2ClientName("KAKAO")
                .email(null)
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifyEmail(verifyDto)
        );

        assertEquals("Email is required for Kakao, Google login.", exception.getMessage());
    }

    @Test
    void verify_email_of_email_is_empty() throws Exception {
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .oAuth2ClientName("KAKAO")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifyEmail(verifyDto)
        );

        assertEquals("Email is required for Kakao, Google login.", exception.getMessage());
    }

    @Test
    void verify_email_of_token_is_empty() throws Exception {
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .oAuth2ClientName("Apple")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifyEmail(verifyDto)
        );

        assertEquals("Token is required for Apple login.", exception.getMessage());
    }

    @Test
    void verify_email_of_kakao_login_should_be_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .oAuth2ClientName("KAKAO")
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
                .oAuth2ClientName("kakao")
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
                .oAuth2ClientName("google")
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
                .oAuth2ClientName("google")
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
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .oAuth2ClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_TOKEN)
                .build();
        given(memberRepository.findByEmail(email))
                .willReturn(Optional.of(Member.builder()
                        .email(email)
                        .build()));

        boolean result = memberLoginService.verifyEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void verify_email_of_apple_login_should_be_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
                .oAuth2ClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_TOKEN)
                .build();
        given(memberRepository.findByEmail(email))
                .willReturn(Optional.empty());

        boolean result = memberLoginService.verifyEmail(verifyDto);
        Assertions.assertFalse(result);
    }
}