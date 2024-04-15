package com.site.reon.aggregate.member.controller;

import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev-oauth")
class AppleOAuth2LoginControllerApiTest extends ApiTest {
    
    @Test 
    void when_authorization_then_redirect_authorization_uri() {
        final var response = AppleOAuth2LoginApiSteps.requestAppleOauth2Authorization();

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
    }
    
    @Test 
    void when_authorizationRedirect_then_redirect_main() {
        final var response = AppleOAuth2LoginApiSteps.requestAuthorizationRedirect();

        Assertions.assertEquals(HttpStatus.FOUND.value(), response.statusCode());
    }

    @Test
    void when_authorizationRedirect_then_redirect_login_fail_page() {
        final var response = AppleOAuth2LoginApiSteps.requestAuthorizationRedirectEmptyParam();

        Assertions.assertEquals(HttpStatus.FOUND.value(), response.statusCode());
    }
}