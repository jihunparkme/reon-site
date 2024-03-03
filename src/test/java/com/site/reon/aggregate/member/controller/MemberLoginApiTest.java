package com.site.reon.aggregate.member.controller;

import com.site.reon.global.ApiTest;
import com.site.reon.global.common.util.infra.RedisUtilService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class MemberLoginApiTest extends ApiTest {

    @Autowired
    private RedisUtilService redisUtilService;

    /**
     * /api/login/verify/email
     */
    @Test
    void when_verify_email_then_return_false() {
        final String authClientName = "kakao";
        final String email = "aaa@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals("false", response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_email_then_return_true() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals("false", response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_email_then_invalid_client_name() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                "", MemberLoginSteps.CLIENT_ID, authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Invalid client name.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_email_then_invalid_client_id() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final var request = MemberLoginSteps.verifyEmailRequest(
                MemberLoginSteps.CLIENT_NAME, "", authClientName, email);

        final var response = MemberLoginSteps.requestVerifyEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Invalid client id.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/oauth2/sign-up
     */
    @Test
    void when_oauth2_sign_up_then_success() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("user", response.jsonPath().getString("data.firstName"));
        Assertions.assertEquals(email, response.jsonPath().getString("data.email"));
        Assertions.assertEquals(roasterSn, response.jsonPath().getString("data.roasterSn"));
        Assertions.assertEquals("picture", response.jsonPath().getString("data.picture"));
        Assertions.assertEquals("KAKAO", response.jsonPath().getString("data.oauthClient"));
    }

    @Test
    void when_oauth2_sign_up_then_invalid_client_name() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                "", MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Invalid client name.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_oauth2_sign_up_then_roasterSn_is_required() {
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("roasterSn is required.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_oauth2_sign_up_then_unsupported_oAuth2_client() {
        final String authClientName = "xxxx";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        final var response = MemberLoginSteps.requestOAuth2SignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/email/sign-up
     */
    @Test
    void when_email_sign_up_then_success() {
        final String email = "aaa@gmail.com";
        final String firstName = "aaron";
        final String lastName = "park";
        final String password = "park123!@#";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.emailSignUpRequest(email, firstName, lastName, password, roasterSn);

        redisUtilService.setValueExpire("sign-up-verified:aaa@gmail.com", "true", 5L);
        final var response = MemberLoginSteps.requestEmailSignUp(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("SUCCESS", response.jsonPath().getString("data"));
    }

    @Test
    void when_email_sign_up_then_email_is_already_registered() {
        final String email = "user@gmail.com";
        final String firstName = "aaron";
        final String lastName = "park";
        final String password = "park123!@#";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.emailSignUpRequest(email, firstName, lastName, password, roasterSn);

        final var response = MemberLoginSteps.requestEmailSignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Your email has not been verified. Please try again.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_email_sign_up_then_password_rules_are_not_satisfied() {
        final String email = "user@gmail.com";
        final String firstName = "aaron";
        final String lastName = "park";
        final String password = "park";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.emailSignUpRequest(email, firstName, lastName, password, roasterSn);

        final var response = MemberLoginSteps.requestEmailSignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("The password must be between 8 and 50 characters long and include letters, numbers, and special characters.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_email_sign_up_then_email_rules_are_not_satisfied() {
        final String email = "user";
        final String firstName = "aaron";
        final String lastName = "park";
        final String password = "park123!@#";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var request = MemberLoginSteps.emailSignUpRequest(email, firstName, lastName, password, roasterSn);

        final var response = MemberLoginSteps.requestEmailSignUp(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("Please check the email format.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/email/auth-code
     */
    @Test
    @Disabled
    void when_send_auth_code() {
        final String purpose = "회원가입";
        final String email = "jihunpark.tech@gmail.com";
        final var request = MemberLoginSteps.sendAuthCodeRequest(purpose, email);

        final var response = MemberLoginSteps.requestSendAuthCode(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("SUCCESS", response.jsonPath().getString("data"));
    }

    @Test
    void when_send_auth_code_then_purpose_required_error() {
        final String purpose = "";
        final String email = "jihunpark.tech@gmail.com";
        final var request = MemberLoginSteps.sendAuthCodeRequest(purpose, email);

        final var response = MemberLoginSteps.requestSendAuthCode(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("purpose is required.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/email/auth-code/verify
     */
    @Test
    void when_verify_auth_code_then_success() {
        final String email = "jihunpark.tech@gmail.com";
        final String authCode = "111111";
        final var request = MemberLoginSteps.verifyAuthCodeRequest(email, authCode);
        redisUtilService.setValueExpire("sign-up:jihunpark.tech@gmail.com", "111111", 5L);

        final var response = MemberLoginSteps.requestVerifyAuthCode(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("SUCCESS", response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_auth_code_then_incorrect_code() {
        final String email = "jihunpark.tech@gmail.com";
        final String authCode = "123456";
        final var request = MemberLoginSteps.verifyAuthCodeRequest(email, authCode);
        redisUtilService.setValueExpire("sign-up:jihunpark.tech@gmail.com", "111111", 1L);

        final var response = MemberLoginSteps.requestVerifyAuthCode(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("The authentication code is incorrect. Please enter it again.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_verify_auth_code_then_exceed_time() throws InterruptedException {
        final String email = "jihunpark.tech@gmail.com";
        final String authCode = "111111";
        final var request = MemberLoginSteps.verifyAuthCodeRequest(email, authCode);
        redisUtilService.setValueExpire("sign-up:jihunpark.tech@gmail.com", "111111", 1L);

        Thread.sleep(1500);
        final var response = MemberLoginSteps.requestVerifyAuthCode(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("The time limit for entering the authentication code has been exceeded. Please try again.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/email
     */
    @Test
    void when_login_email_then_success() {
        final String email = "user@gmail.com";
        final String firstName = "aaron";
        final String lastName = "park";
        final String password = "park123!@#";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        signUpEmailMember(email, firstName, lastName, password, roasterSn);
        final var request = MemberLoginSteps.loginEmailRequest(email, password);

        final var response = MemberLoginSteps.requestLoginEmail(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("1", response.jsonPath().getString("count"));
        Assertions.assertEquals("aaron", response.jsonPath().getString("data.firstName"));
        Assertions.assertEquals("park", response.jsonPath().getString("data.lastName"));
        Assertions.assertEquals(email, response.jsonPath().getString("data.email"));
        Assertions.assertEquals(roasterSn, response.jsonPath().getString("data.roasterSn"));
        Assertions.assertEquals(null, response.jsonPath().getString("data.picture"));
        Assertions.assertEquals("EMPTY", response.jsonPath().getString("data.oauthClient"));
    }

    @Test
    void when_login_email_then_aaa() {
        final String email = "user@gmail.com";
        final String password = "user";
        final var request = MemberLoginSteps.loginEmailRequest(email, password);

        final var response = MemberLoginSteps.requestLoginEmail(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("자격 증명에 실패하였습니다.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    /**
     * /api/login/withdraw
     */
    @Test
    void when_withdraw_email_member_then_success() {
        final String authClientName = "";
        final String email = "user@gmail.com";
        final String firstName = "aaron";
        final String lastName = "park";
        final String password = "park123!@#";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        signUpEmailMember(email, firstName, lastName, password, roasterSn);
        final var request = MemberLoginSteps.withdrawRequest(email, authClientName);

        final var response = MemberLoginSteps.requestWithdraw(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals("true", response.jsonPath().getString("data"));
    }

    @Test
    void when_withdraw_oauth2_member_then_success() {
        final String authClientName = "google";
        final String email = "aaron@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        signUpOauth2Member(authClientName, email, roasterSn);
        final var request = MemberLoginSteps.withdrawRequest(email, authClientName);

        final var response = MemberLoginSteps.requestWithdraw(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("200", response.jsonPath().getString("status"));
        Assertions.assertEquals("OK", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("true", response.jsonPath().getString("success"));
        Assertions.assertEquals(null, response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals("true", response.jsonPath().getString("data"));
    }

    @Test
    void when_withdraw_then_no_registered_member() {
        final String authClientName = "";
        final String email = "aaron@gmail.com";
        final var request = MemberLoginSteps.withdrawRequest(email, authClientName);

        final var response = MemberLoginSteps.requestWithdraw(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("No registered member information.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    @Test
    void when_withdraw_then_unsupported_oauth2_client() {
        final String authClientName = "XXX";
        final String email = "aaron@gmail.com";
        final var request = MemberLoginSteps.withdrawRequest(email, authClientName);

        final var response = MemberLoginSteps.requestWithdraw(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        Assertions.assertEquals("400", response.jsonPath().getString("status"));
        Assertions.assertEquals("BAD_REQUEST", response.jsonPath().getString("httpStatusCode"));
        Assertions.assertEquals("false", response.jsonPath().getString("success"));
        Assertions.assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", response.jsonPath().getString("message"));
        Assertions.assertEquals("0", response.jsonPath().getString("count"));
        Assertions.assertEquals(null, response.jsonPath().getString("data"));
    }

    private void signUpOauth2Member(final String authClientName, final String email, final String roasterSn) {
        final var request = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);
        MemberLoginSteps.requestOAuth2SignUp(request);
    }

    private void signUpEmailMember(final String email, final String firstName, final String lastName, final String password, final String roasterSn) {
        final var request = MemberLoginSteps.emailSignUpRequest(email, firstName, lastName, password, roasterSn);
        redisUtilService.setValueExpire("sign-up-verified:user@gmail.com", "true", 5L);
        MemberLoginSteps.requestEmailSignUp(request);
    }
}
