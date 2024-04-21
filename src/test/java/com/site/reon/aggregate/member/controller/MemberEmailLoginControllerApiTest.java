package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.controller.steps.MemberEmailLoginApiSteps;
import com.site.reon.global.ApiTest;
import com.site.reon.global.common.util.infra.RedisUtilService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class MemberEmailLoginControllerApiTest extends ApiTest {

    @Autowired
    private RedisUtilService redisUtilService;

    private static final String EMAIL = "example@gmail.com";

    @Test
    @Disabled
    void when_email_sign_up_then_success() {
        final var request = MemberEmailLoginApiSteps.signUpRequest();

        redisUtilService.setValueExpire("sign-up-verified:example@gmail.com", "true", 5L);
        final var response = MemberEmailLoginApiSteps.requestSignUp(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("SUCCESS"));
    }

    @Test
    @Disabled
    void when_authorize_then_success() {
        final var signUpRequest = MemberEmailLoginApiSteps.signUpRequest();
        redisUtilService.setValueExpire("sign-up-verified:example@gmail.com", "true", 5L);
        MemberEmailLoginApiSteps.requestSignUp(signUpRequest);

        final var request = MemberEmailLoginApiSteps.authenticateRequest();

        final var response = MemberEmailLoginApiSteps.requestAuthenticate(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("1", response.jsonPath().getString("id"));
        Assertions.assertEquals("aaron", response.jsonPath().getString("firstName"));
        Assertions.assertEquals("park", response.jsonPath().getString("lastName"));
        Assertions.assertEquals(EMAIL, response.jsonPath().getString("email"));
        Assertions.assertEquals("EMPTY", response.jsonPath().getString("oauthClient"));
    }
    
    @Test
    @Disabled
    void when_sendAuthenticationCodeByEmail_then_success() {
        final var request = MemberEmailLoginApiSteps.sendAuthCodeRequest();

        final var response = MemberEmailLoginApiSteps.requestSendAuthCode(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("SUCCESS"));
    }
    
    @Test
    @Disabled
    void when_verifyAuthenticationCode_then_success() {
        final var sendAuthCodeRequest = MemberEmailLoginApiSteps.sendAuthCodeRequest();
        MemberEmailLoginApiSteps.requestSendAuthCode(sendAuthCodeRequest);
        final Optional<String> authCodeOpt = redisUtilService.getValueOf("sign-up:example@gmail.com");

        final var request = MemberEmailLoginApiSteps.verifyAuthCodeRequest(EMAIL, authCodeOpt.get());

        final var response = MemberEmailLoginApiSteps.requestVerifyAuthCode(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("SUCCESS"));
    }
}