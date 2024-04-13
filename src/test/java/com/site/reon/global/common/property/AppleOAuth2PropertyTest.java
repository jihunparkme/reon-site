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
class AppleOAuth2PropertyTest {

    @Autowired
    AppleOAuth2Property property;

    @Test 
    void property() {
        log.info("teamId: {}", property.getTeamId());
        log.info("clientId: {}", property.getClientId());
        log.info("keyId: {}", property.getKeyId());
        log.info("redirectUri: {}", property.getRedirectUri());
        log.info("responseMode: {}", property.getResponseMode());
        log.info("nonce: {}", property.getNonce());
        log.info("authorizationUri: {}", property.getAuthorizationUri());
        log.info("responseType: {}", property.getResponseType());
        log.info("scope: {}", property.getScope());
        log.info("clientName: {}", property.getClientName());
        Assertions.assertEquals("Apple", property.getClientName());
    }
}