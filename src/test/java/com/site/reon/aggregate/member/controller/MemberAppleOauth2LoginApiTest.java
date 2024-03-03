package com.site.reon.aggregate.member.controller;

import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev-oauth")
class MemberAppleOauth2LoginApiTest extends ApiTest {
    
    @Test 
    void authorization() {
        final var response = AppleOauth2Steps.requestAppleOauth2Authorization();

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
    }
    
    @Test 
    void authorizationRedirect() {
        final var response = AppleOauth2Steps.requestAuthorizationRedirect();

        Assertions.assertEquals(HttpStatus.FOUND.value(), response.statusCode());
    }
}