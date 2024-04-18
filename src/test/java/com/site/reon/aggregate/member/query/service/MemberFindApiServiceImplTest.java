package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.query.dto.ApiEmailVerifyRequest;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberFindApiServiceImplTest {

    private MemberRepository memberRepository = mock(MemberRepository.class);
    private MemberFindApiService memberFindApiService = new MemberFindApiServiceImpl(memberRepository);
    private final static String APPLE_LOGIN_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYWFyb24ucGFyayIsImV4cCI6MTcwNjQ0NTUyOSwiaWF0IjoxNzA2MzU5MTI5LCJzdWIiOiIwMDAzODUuMDQ3c2dmNjZhYnM2NGQ2MGE0MDZkNWQ0YjNiNHgydjIuMTk5MyIsImNfaGFzaCI6IkYyWWRiN0R2RUJZaE9vUElHdGhEb0ciLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNzA2MzU5MTI5LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.8DWNWY3PkDRdXzAjmrcaWH9p0tvjmg3ieOH4MZXz7Gs";
    private final static String APPLE_LOGIN_INVALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYWFyb24ucGFyayIsImV4cCI6MTcwNjQ0NTUyOSwiaWF0IjoxNzA2MzU5MTI5LCJzdWIiOiIwMDAzODUuMDQ3c2dmNjZhYnM2NGQ2MGE0MDZkNWQ0YjNiNHgydjIuMTk5MyIsImNfaGFzaCI6IkYyWWRiN0R2RUJZaE9vUElHdGhEb0ciLCJlbWFpbF92ZXJpZmllZCI6InRydWUiLCJhdXRoX3RpbWUiOjE3MDYzNTkxMjksIm5vbmNlX3N1cHBvcnRlZCI6dHJ1ZX0.JNt_m61ME7Lf38SnxZcyq7GxfFzPQ6TqsS72i_ixMxQ";

    private String email = "user@gmail.com";
    private Optional<Member> memberOpt = Optional.of(Member.builder()
            .email(email)
            .build());

    @Test
    void when_verifySocialEmail_then_null_email_exception() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("KAKAO")
                .email(null)
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberFindApiService.verifySocialEmail(verifyDto)
        );

        assertEquals("Email is required for Kakao, Google login.", exception.getMessage());
    }

    @Test
    void when_verifySocialEmail_then_empty_email_exception() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("KAKAO")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberFindApiService.verifySocialEmail(verifyDto)
        );

        assertEquals("Email is required for Kakao, Google login.", exception.getMessage());
    }

    @Test
    void when_verifySocialEmail_then_empty_token_exception() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("Apple")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberFindApiService.verifySocialEmail(verifyDto)
        );

        assertEquals("Token is required for Apple login.", exception.getMessage());
    }

    @Test
    void when_verifySocialEmail_kakao_account_then_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("KAKAO")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.KAKAO))
                .willReturn(memberOpt);

        boolean result = memberFindApiService.verifySocialEmail(verifyDto);

        Assertions.assertTrue(result);
    }

    @Test
    void when_verifySocialEmail_kakao_account_then_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("kakao")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.KAKAO))
                .willReturn(Optional.empty());

        boolean result = memberFindApiService.verifySocialEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void when_verifySocialEmail_google_account_then_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("google")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.GOOGLE))
                .willReturn(memberOpt);

        boolean result = memberFindApiService.verifySocialEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void when_verifySocialEmail_google_account_then_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("google")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.GOOGLE))
                .willReturn(Optional.empty());

        boolean result = memberFindApiService.verifySocialEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void when_verifySocialEmail_apple_account_then_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_TOKEN)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.APPLE))
                .willReturn(memberOpt);

        boolean result = memberFindApiService.verifySocialEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void when_verifySocialEmail_apple_account_then_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_TOKEN)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.APPLE))
                .willReturn(Optional.empty());

        boolean result = memberFindApiService.verifySocialEmail(verifyDto);
        Assertions.assertFalse(result);
    }
    @Test
    void when_verifySocialEmail_apple_account_then_token_exception() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_INVALID_TOKEN)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberFindApiService.verifySocialEmail(verifyDto)
        );

        assertEquals("The token information is invalid.", exception.getMessage());
    }

}