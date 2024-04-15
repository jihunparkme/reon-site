package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.global.ApiTest;
import com.site.reon.global.common.util.infra.RedisUtilService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GoogleOAuth2WithdrawalControllerApiTest extends ApiTest {

    @Autowired
    RedisUtilService redisUtilService;

    @Test
    void when_signalWithdrawal_then_return_success() {
        final WithdrawRequest request = WithdrawRequest.builder()
                .email("example@gmail.com")
                .authClientName("google")
                .build();

        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/login/oauth2/withdraw/google/signal")
                .then()
                .log().all().extract();

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("SUCCESS", response.jsonPath().getString("data"));

        final Optional<String> result = redisUtilService.getValueOf("withdraw:google:example@gmail.com");
        assertTrue(result.isPresent());
    }
}