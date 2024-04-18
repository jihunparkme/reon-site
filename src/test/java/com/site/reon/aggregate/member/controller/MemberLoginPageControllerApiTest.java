package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.controller.steps.MemberLoginPageApiSteps;
import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberLoginPageControllerApiTest extends ApiTest {

    @Test
    void when_loginMainPage_then_return_login_page() {
        final var response = MemberLoginPageApiSteps.requestLoginMainPage();

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("<h1>LOGIN</h1>"));
    }

    @Test
    void when_emailLoginPage_then_return_email_page() {
        final var response = MemberLoginPageApiSteps.requestEmailLoginPage();

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("<h1>LOGIN</h1>"));
    }

    @Test
    void when_oauth2LoginFailPage_then_return_oauth2_fail_page() {
        final var response = MemberLoginPageApiSteps.requestOAuth2LoginFailPage();

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("alert(\"로그인에 실패하였습니다. 다시 시도해 주세요.\");"));
    }
}