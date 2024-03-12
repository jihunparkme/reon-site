package com.site.reon.global.common.property;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@ActiveProfiles({"test", "dev-oauth"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class GoogleOauth2PropertyTest {
    @Autowired
    GoogleOauth2Property property;

    @Test
    void property() {
        log.info("clientId: {}", property.getClientId());
        log.info("clientSecret: {}", property.getClientSecret());
        log.info("scope: {}", property.getScope());
        log.info("responseType: {}", property.getResponseType());
        log.info("authorizationUri: {}", property.getAuthorizationUri());
        log.info("redirectUri: {}", property.getRedirectUri());
        log.info("state: {}", property.getState());
        log.info("accessType: {}", property.getAccessType());
        log.info("includeGrantedScopes: {}", property.getIncludeGrantedScopes());
        Assertions.assertEquals("Google", property.getClientName());
    }
}