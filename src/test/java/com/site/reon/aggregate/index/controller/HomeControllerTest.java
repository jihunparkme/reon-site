package com.site.reon.aggregate.index.controller;

import com.site.reon.global.ApiTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest extends ApiTest {

    @Test
    void when_home_then_return_index_page() {
        final var response = RestAssured.given().log().all()
                .when()
                .get("/")
                .then()
                .log().all().extract();

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertThat(response.body().asString(), containsString("/img/logo/reonai-logo.png"));
    }
}
