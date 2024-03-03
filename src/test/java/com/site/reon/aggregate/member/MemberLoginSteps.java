package com.site.reon.aggregate.member;

import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MemberLoginSteps {

    public final static String CLIENT_NAME = "reon";
    public final static String CLIENT_ID = "235df110-bd70-11ee-aa8b-e30685fde2fa";


    public static ApiEmailVerifyRequest verifyEmailRequest(
            final String clientName,
            final String clientId,
            final String authClientName,
            final String email) {
        return ApiEmailVerifyRequest.builder()
                .clientName(clientName)
                .clientId(clientId)
                .authClientName(authClientName)
                .email(email)
                .build();
    }

    public static ExtractableResponse<Response> requestVerifyEmail(final ApiEmailVerifyRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/verify/email")
                .then()
                .log().all().extract();
    }
}
