package com.site.reon.global.common.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ReonAppPropertyTest {

    @Autowired
    ReonAppProperty reonAppProperty;

    @Test
    void method() throws Exception {
        Assertions.assertEquals("reon", reonAppProperty.getClientName());
        Assertions.assertEquals("235df110-bd70-11ee-aa8b-e30685fde2fa", reonAppProperty.getClientId());
    }
}