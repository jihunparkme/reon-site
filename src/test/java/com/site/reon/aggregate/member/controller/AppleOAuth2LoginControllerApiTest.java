package com.site.reon.aggregate.member.controller;

import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("dev-oauth")
class AppleOAuth2LoginControllerApiTest extends ApiTest {
    
    @Test 
    void when_authorization_then_redirect_authorization_uri() {
        final var response = AppleOAuth2LoginApiSteps.requestAppleOauth2Authorization();

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("<title>Sign in with Apple ID</title>"));
    }
    
    @Test 
    void when_authorizationRedirect_then_redirect_main() {
        final var response = AppleOAuth2LoginApiSteps.requestAuthorizationRedirect();

        assertEquals(HttpStatus.FOUND.value(), response.statusCode());
        assertTrue(response.header("Location").contains("/"));
    }

    @Test
    void when_authorizationRedirect_then_redirect_login_fail_page() {
        final var response = AppleOAuth2LoginApiSteps.requestAuthorizationRedirectEmptyParam();

        assertEquals(HttpStatus.FOUND.value(), response.statusCode());
        assertTrue(response.header("Location").contains("/login/oauth2/fail"));
    }
}