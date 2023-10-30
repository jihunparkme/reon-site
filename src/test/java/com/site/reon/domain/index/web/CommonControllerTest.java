package com.site.reon.domain.index.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CommonControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getActiveProfiles() {
        String profile = restTemplate.getForObject("/profile", String.class);
        System.out.println(profile);
    }
}