package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.member.controller.MemberLoginSteps;
import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RoastingRecordApiTest extends ApiTest {

    private final static String AUTH_CLIENT_NAME = "kakao";
    private final static String EMAIL = "user@gmail.com";

    @Test
    void upload_record() {
        final var request = RoastingRecordSteps.getRoastingRecordRequest();

        final var response = RoastingRecordSteps.requestUploadRoastingRecord(request);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    @Test
    void find_roasting_record_list() {
        // given
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        final long memberId = getMemberId(AUTH_CLIENT_NAME, EMAIL);
        requestUploadRoastingRecord();
        requestUploadRoastingRecord();

        // when
        final var request = RoastingRecordSteps.getRoastingRecordListRequest(memberId);

        // then
        final var response = RoastingRecordSteps.requestRoastingRecordList(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("2", response.jsonPath().getString("count"));
        Assertions.assertEquals("1", response.jsonPath().getString("data[0].id"));
        Assertions.assertEquals("test roasting222", response.jsonPath().getString("data[0].title"));
        Assertions.assertEquals("2", response.jsonPath().getString("data[1].id"));
        Assertions.assertEquals("test roasting222", response.jsonPath().getString("data[1].title"));
    }

    @Test
    void find_roasting_record() {
        // given
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        final long memberId = getMemberId(AUTH_CLIENT_NAME, EMAIL);
        requestUploadRoastingRecord();
        requestUploadRoastingRecord();

        // when
        final long recordId = 1L;
        final var request = RoastingRecordSteps.getApiRoastingRecordRequest(memberId);

        // then
        final var response = RoastingRecordSteps.requestApiRoastingRecord(request, recordId);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("1", response.jsonPath().getString("data.id"));
        Assertions.assertEquals("test roasting222", response.jsonPath().getString("data.title"));
    }

    private void requestUploadRoastingRecord() {
        final var roastingRecordRequest = RoastingRecordSteps.getRoastingRecordRequest();

        RoastingRecordSteps.requestUploadRoastingRecord(roastingRecordRequest);
    }

    private long getMemberId(final String authClientName, final String email) {
        final var myPageRequest = MemberLoginSteps.myPageRequest(authClientName, email);
        final var myPageResponse = MemberLoginSteps.requestMyPage(myPageRequest);
        Assertions.assertEquals(HttpStatus.OK.value(), myPageResponse.statusCode());

        final String memberId = myPageResponse.jsonPath().getString("data.id");
        Assertions.assertEquals("1", memberId);

        return Long.parseLong(memberId);
    }

    private void requestOAuth2SignUp(final String authClientName, final String email) {
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var oAuth2SignUpRequest = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);

        MemberLoginSteps.requestOAuth2SignUp(oAuth2SignUpRequest);
    }
}
/**
 * * - 프로파일 이름
 *      * - 개수
 *      * - 날짜, 시간 (2024.2.14)
 *      * - 프로파일 아이디 (09:09:38)
 */