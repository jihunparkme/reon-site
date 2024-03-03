package com.site.reon.aggregate.member.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class AppleOauth2Steps {
    public static ExtractableResponse<Response> requestAppleOauth2Authorization() {
        return RestAssured.given().log().all()
                .when()
                .get("/login/oauth2/authorization/apple")
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> requestAuthorizationRedirect() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .formParam("id_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYWFyb24ucGFyayIsImV4cCI6MTcwNjQ0NTUyOSwiaWF0IjoxNzA2MzU5MTI5LCJzdWIiOiIwMDAzODUuMDQ3c2dmNjZhYnM2NGQ2MGE0MDZkNWQ0YjNiNHgydjIuMTk5MyIsImNfaGFzaCI6IkYyWWRiN0R2RUJZaE9vUElHdGhEb0ciLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNzA2MzU5MTI5LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.8DWNWY3PkDRdXzAjmrcaWH9p0tvjmg3ieOH4MZXz7Gs")
                .when()
                .post("/login/oauth2/apple/redirect")
                .then()
                .log().all().extract();
    }
}
