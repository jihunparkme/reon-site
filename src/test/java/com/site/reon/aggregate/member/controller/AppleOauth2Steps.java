package com.site.reon.aggregate.member.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AppleOauth2Steps {
    public static ExtractableResponse<Response> requestAppleOauth2Authorization() {
        return RestAssured.given().log().all()
                .when()
                .get("/login/oauth2/authorization/apple")
                .then()
                .log().all().extract();
    }
}
