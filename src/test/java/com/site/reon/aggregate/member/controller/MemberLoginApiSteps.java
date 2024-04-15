package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.command.dto.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.command.dto.ApiRegisterMemberSerialNo;
import com.site.reon.aggregate.member.command.dto.ApiWithdrawRequest;
import com.site.reon.aggregate.member.controller.dto.*;
import com.site.reon.aggregate.member.query.dto.ApiEmailVerifyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MemberLoginApiSteps {

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

    public static ApiOAuth2SignUpRequest oAuth2SignUpRequest(
            final String clientName,
            final String clientId,
            final String authClientName,
            final String email,
            final String roasterSn) {
        return ApiOAuth2SignUpRequest.builder()
                .clientName(clientName)
                .clientId(clientId)
                .authClientName(authClientName)
                .email(email)
                .firstName("user")
                .picture("picture")
                .roasterSn(roasterSn)
                .build();
    }

    public static ExtractableResponse<Response> requestOAuth2SignUp(final ApiOAuth2SignUpRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/oauth2/sign-up")
                .then()
                .log().all().extract();
    }

    public static ApiSignUpRequest emailSignUpRequest(
            final String email,
            final String firstName,
            final String lastName,
            final String password,
            final String roasterSn) {
        return ApiSignUpRequest.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .roasterSn(roasterSn)
                .build();

    }

    public static ExtractableResponse<Response> requestEmailSignUp(final ApiSignUpRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/email/sign-up")
                .then()
                .log().all().extract();
    }

    public static ApiEmailAuthCodeRequest sendAuthCodeRequest(final String purpose, final String email) {
        return ApiEmailAuthCodeRequest.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .purpose(purpose)
                .email(email)
                .build();

    }

    public static ExtractableResponse<Response> requestSendAuthCode(final ApiEmailAuthCodeRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/email/auth-code")
                .then()
                .log().all().extract();
    }

    public static ApiEmailAuthCodeVerifyRequest verifyAuthCodeRequest(final String email, final String authCode) {
        return ApiEmailAuthCodeVerifyRequest.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .email(email)
                .authCode(authCode)
                .build();
    }

    public static ExtractableResponse<Response> requestVerifyAuthCode(final ApiEmailAuthCodeVerifyRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/email/auth-code/verify")
                .then()
                .log().all().extract();
    }

    public static ApiLoginRequest loginEmailRequest(final String email, final String password) {
        return ApiLoginRequest.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .email(email)
                .password(password)
                .build();
    }

    public static ExtractableResponse<Response> requestLoginEmail(final ApiLoginRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/email")
                .then()
                .log().all().extract();
    }

    public static ApiWithdrawRequest withdrawRequest(final String email, final String authClientName) {
        return ApiWithdrawRequest.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .email(email)
                .authClientName(authClientName)
                .build();
    }

    public static ExtractableResponse<Response> requestWithdraw(final ApiWithdrawRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/withdraw")
                .then()
                .log().all().extract();
    }

    public static ApiMyPageRequest myPageRequest(final String authClientName, final String email) {
        return ApiMyPageRequest.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .email(email)
                .authClientName(authClientName)
                .build();
    }

    public static ExtractableResponse<Response> requestMyPage(final ApiMyPageRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/info")
                .then()
                .log().all().extract();
    }

    public static ApiRegisterMemberSerialNo registerMemberSerialNoRequest() {
        return ApiRegisterMemberSerialNo.builder()
                .clientName(CLIENT_NAME)
                .clientId(CLIENT_ID)
                .serialNo("R2N0BK-0009-20240322")
                .build();
    }

    public static ExtractableResponse<Response> requestRegisterMemberSerialNo(final ApiRegisterMemberSerialNo request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/member/1/serial-no")
                .then()
                .log().all().extract();
    }
}
