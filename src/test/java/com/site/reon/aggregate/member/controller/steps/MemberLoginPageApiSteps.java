package com.site.reon.aggregate.member.controller.steps;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberLoginPageApiSteps {
    public static ExtractableResponse<Response> requestLoginMainPage() {
        return RestAssured.given().log().all()
                .when()
                .get("/login")
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> requestEmailLoginPage() {
        return RestAssured.given().log().all()
                .when()
                .get("/login/email")
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> requestOAuth2LoginFailPage() {
        return RestAssured.given().log().all()
                .when()
                .get("/login/oauth2/fail")
                .then()
                .log().all().extract();
    }
}
