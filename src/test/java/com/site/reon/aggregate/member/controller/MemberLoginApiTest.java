package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.MemberLoginSteps;
import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class MemberLoginApiTest extends ApiTest {

    /**
     * /api/login/verify/email
     */
    @Test
    void when_verify_email_then_return_false() {
        final String authClientName = "kakao";
        final String email = "aaa@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals("false", response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_email_then_return_true() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals("false", response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_email_then_invalid_client_name() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                "", MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Invalid client name.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_email_then_invalid_client_id() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, "", authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Invalid client id.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/oauth2/sign-up
     */
    @Test
    void when_oauth2_sign_up_then_success() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("user", response.jsonPath().getString("data.firstName"));
        Assertions.assertEquals(email, response.jsonPath().getString("data.email"));
        Assertions.assertEquals(roasterSn, response.jsonPath().getString("data.roasterSn"));
        Assertions.assertEquals("picture", response.jsonPath().getString("data.picture"));
        Assertions.assertEquals("KAKAO", response.jsonPath().getString("data.oauthClient"));
    }

    @Test
    void when_oauth2_sign_up_then_invalid_client_name() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                "", MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Invalid client name.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_oauth2_sign_up_then_roasterSn_is_required() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("roasterSn is required.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_oauth2_sign_up_then_unsupported_oAuth2_client() {
        final String authClientName = "xxxx";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }
}