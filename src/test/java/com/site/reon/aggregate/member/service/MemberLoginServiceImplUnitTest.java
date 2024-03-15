package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.WithdrawRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpRequest;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberLoginServiceImplUnitTest {

    private MemberRepository memberRepository = mock(MemberRepository.class);
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private AuthenticationManagerBuilder authenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);
    private MemberAuthCodeService memberAuthCodeService = mock(MemberAuthCodeService.class);
    private MemberLoginService memberLoginService = new MemberLoginServiceImpl(memberRepository, passwordEncoder, authenticationManagerBuilder, memberAuthCodeService, null);
    private final static String APPLE_LOGIN_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYWFyb24ucGFyayIsImV4cCI6MTcwNjQ0NTUyOSwiaWF0IjoxNzA2MzU5MTI5LCJzdWIiOiIwMDAzODUuMDQ3c2dmNjZhYnM2NGQ2MGE0MDZkNWQ0YjNiNHgydjIuMTk5MyIsImNfaGFzaCI6IkYyWWRiN0R2RUJZaE9vUElHdGhEb0ciLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNzA2MzU5MTI5LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.8DWNWY3PkDRdXzAjmrcaWH9p0tvjmg3ieOH4MZXz7Gs";

    private String email = "user@gmail.com";
    private Optional<Member> memberOpt = Optional.of(Member.builder()
            .email(email)
            .build());

    @Test
    void verify_email_of_email_is_null() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("KAKAO")
                .email(null)
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifySocialEmail(verifyDto)
        );

        assertEquals("Email is required for Kakao, Google login.", exception.getMessage());
    }

    @Test
    void verify_email_of_email_is_empty() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("KAKAO")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifySocialEmail(verifyDto)
        );

        assertEquals("Email is required for Kakao, Google login.", exception.getMessage());
    }

    @Test
    void verify_email_of_token_is_empty() throws Exception {
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("Apple")
                .email("")
                .token(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.verifySocialEmail(verifyDto)
        );

        assertEquals("Token is required for Apple login.", exception.getMessage());
    }

    @Test
    void verify_email_of_kakao_login_should_be_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("KAKAO")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.KAKAO))
                .willReturn(memberOpt);

        boolean result = memberLoginService.verifySocialEmail(verifyDto);

        Assertions.assertTrue(result);
    }

    @Test
    void verify_email_of_kakao_login_should_be_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("kakao")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.KAKAO))
                .willReturn(Optional.empty());

        boolean result = memberLoginService.verifySocialEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void verify_email_of_google_login_should_be_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("google")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.GOOGLE))
                .willReturn(memberOpt);

        boolean result = memberLoginService.verifySocialEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void verify_email_of_google_login_should_be_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("google")
                .email(email)
                .token(null)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.GOOGLE))
                .willReturn(Optional.empty());

        boolean result = memberLoginService.verifySocialEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void verify_email_of_apple_login_should_be_return_true() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_TOKEN)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.APPLE))
                .willReturn(memberOpt);

        boolean result = memberLoginService.verifySocialEmail(verifyDto);
        Assertions.assertTrue(result);
    }

    @Test
    void verify_email_of_apple_login_should_be_return_false() throws Exception {
        String email = "user@gmail.com";
        ApiEmailVerifyRequest verifyDto = ApiEmailVerifyRequest.builder()
                .authClientName("apple")
                .email(null)
                .token(APPLE_LOGIN_TOKEN)
                .build();
        given(memberRepository.findByEmailAndOAuthClient(email, OAuth2Client.APPLE))
                .willReturn(Optional.empty());

        boolean result = memberLoginService.verifySocialEmail(verifyDto);
        Assertions.assertFalse(result);
    }

    @Test
    void oAuth2SignUp_success() throws Exception {
        String email = "user@gmail.com";
        ApiOAuth2SignUpRequest apiOAuth2SignUp = ApiOAuth2SignUpRequest.builder()
                .roasterSn("asfdasfeasfdsasdfas")
                .email(email)
                .firstName("aaron")
                .picture("safddsafdsafs")
                .authClientName("kakao")
                .build();
        final Member expected = Member.builder()
                .id(3L)
                .roasterSn("asfdasfeasfdsasdfas")
                .email(email)
                .firstName("aaron")
                .picture("safddsafdsafs")
                .oAuthClient(OAuth2Client.KAKAO)
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .build();

        given(memberRepository.save(any()))
                .willReturn(expected);

        MemberDto member = memberLoginService.oAuth2SignUp(apiOAuth2SignUp);
        Assertions.assertEquals("asfdasfeasfdsasdfas", member.getRoasterSn());
        Assertions.assertEquals("aaron", member.getFirstName());
        Assertions.assertEquals("safddsafdsafs", member.getPicture());
        Assertions.assertEquals(OAuth2Client.KAKAO, member.getOAuthClient());
    }

    @Test
    void oAuth2SignUp_fail() throws Exception {
        String email = "user@gmail.com";
        ApiOAuth2SignUpRequest apiOAuth2SignUp = ApiOAuth2SignUpRequest.builder()
                .roasterSn("asfdasfeasfdsasdfas")
                .email(email)
                .firstName("aaron")
                .picture("safddsafdsafs")
                .authClientName("aaaa")
                .build();


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.oAuth2SignUp(apiOAuth2SignUp)
        );

        assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", exception.getMessage());
    }

    @Test
    void withdraw_success() throws Exception {
        String email = "user@gmail.com";
        WithdrawRequest request = WithdrawRequest.builder()
                .email(email)
                .authClientName("")
                .build();

        given(memberRepository.findByEmailAndOAuthClient(any(), any()))
                .willReturn(memberOpt);
        given(memberRepository.deleteByEmailAndOAuthClient(any(), any()))
                .willReturn(1);

        boolean result = memberLoginService.withdraw(request);

        Assertions.assertTrue(result);
    }

    @Test
    void withdraw_fail_invalid_client_name() throws Exception {
        String email = "user@gmail.com";
        WithdrawRequest request = WithdrawRequest.builder()
                .email(email)
                .authClientName("XXX")
                .build();


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.withdraw(request)
        );

        assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", exception.getMessage());
    }

    @Test
    void withdraw_fail_not_exist_member() throws Exception {
        String email = "xxx@gmail.com";
        WithdrawRequest request = WithdrawRequest.builder()
                .email(email)
                .authClientName("google")
                .build();
        given(memberRepository.findByEmailAndOAuthClient(any(), any()))
                .willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberLoginService.withdraw(request)
        );

        assertEquals("No registered member information.", exception.getMessage());
    }
}