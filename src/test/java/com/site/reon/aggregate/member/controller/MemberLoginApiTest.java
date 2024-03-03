package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.MemberLoginSteps;
import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class MemberLoginApiTest extends ApiTest {

    @Test
    void when_verify_email_then_return_false() {
        final String authClientName = "kakao";
        final String email = "aaa@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals(response.jsonPath().getString("status"), "200");
        Assertions.assertEquals(response.jsonPath().getString("httpStatusCode"), "OK");
        Assertions.assertEquals(response.jsonPath().getString("success"), "true");
        Assertions.assertEquals(response.jsonPath().getString("message"), null);
        Assertions.assertEquals(response.jsonPath().getString("count"), "0");
        Assertions.assertEquals(response.jsonPath().getString("data"), "false");
    }

    @Test
    void when_verify_email_then_return_true() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals(response.jsonPath().getString("status"), "200");
        Assertions.assertEquals(response.jsonPath().getString("httpStatusCode"), "OK");
        Assertions.assertEquals(response.jsonPath().getString("success"), "true");
        Assertions.assertEquals(response.jsonPath().getString("message"), null);
        Assertions.assertEquals(response.jsonPath().getString("count"), "0");
        Assertions.assertEquals(response.jsonPath().getString("data"), "false");
    }

    @Test
    void when_verify_email_then_invalid_client_name() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                "", MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals(response.jsonPath().getString("status"), "400");
        Assertions.assertEquals(response.jsonPath().getString("httpStatusCode"), "BAD_REQUEST");
        Assertions.assertEquals(response.jsonPath().getString("success"), "false");
        Assertions.assertEquals(response.jsonPath().getString("message"), "Invalid client name.");
        Assertions.assertEquals(response.jsonPath().getString("count"), "0");
        Assertions.assertEquals(response.jsonPath().getString("data"), null);
    }

    @Test
    void when_verify_email_then_invalid_client_id() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, "", authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals(response.jsonPath().getString("status"), "400");
        Assertions.assertEquals(response.jsonPath().getString("httpStatusCode"), "BAD_REQUEST");
        Assertions.assertEquals(response.jsonPath().getString("success"), "false");
        Assertions.assertEquals(response.jsonPath().getString("message"), "Invalid client id.");
        Assertions.assertEquals(response.jsonPath().getString("count"), "0");
        Assertions.assertEquals(response.jsonPath().getString("data"), null);
    }
}
