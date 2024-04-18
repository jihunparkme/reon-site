package com.site.reon.aggregate.member.controller.steps;

import com.site.reon.aggregate.member.command.dto.LoginRequest;
import com.site.reon.aggregate.member.command.dto.SignUpRequest;
import com.site.reon.aggregate.member.controller.dto.EmailAuthCodeRequest;
import com.site.reon.aggregate.member.controller.dto.EmailAuthCodeVerifyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MemberEmailLoginApiSteps {

    private static final String EMAIL = "example@gmail.com";

    public static SignUpRequest signUpRequest() {
        return SignUpRequest.builder()
                .email(EMAIL)
                .firstName("aaron")
                .lastName("park")
                .password("aaa123!@#")
                .build();
    }

    public static ExtractableResponse<Response> requestSignUp(final SignUpRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/login/email/sign-up")
                .then()
                .log().all().extract();
    }

    public static LoginRequest authenticateRequest() {
        return LoginRequest.builder()
                .email(EMAIL)
                .password("aaa123!@#")
                .build();
    }

    public static ExtractableResponse<Response> requestAuthenticate(LoginRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/login/email/authenticate")
                .then()
                .log().all().extract();
    }

    public static EmailAuthCodeRequest sendAuthCodeRequest() {
        return new EmailAuthCodeRequest(EMAIL);
    }

    public static ExtractableResponse<Response> requestSendAuthCode(EmailAuthCodeRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/login/email/auth-code")
                .then()
                .log().all().extract();
    }

    public static EmailAuthCodeVerifyRequest verifyAuthCodeRequest(String email, String authCode) {
        return new EmailAuthCodeVerifyRequest(email, authCode);
    }

    public static ExtractableResponse<Response> requestVerifyAuthCode(EmailAuthCodeVerifyRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/login/email/auth-code/verify")
                .then()
                .log().all().extract();
    }
}
