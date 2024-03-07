package com.site.reon.aggregate.record.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.site.reon.aggregate.member.controller.MemberLoginSteps;
import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RoastingRecordApiTest extends ApiTest {

    @Test
    void upload_record() {
        final var request = RoastingRecordSteps.getRoastingRecordRequest();

        final var response = RoastingRecordSteps.requestUploadRoastingRecord(request);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    @Test
    void RoastingRecords() throws JsonProcessingException {
        // requestOAuth2SignUp
        final String authClientName = "kakao";
        final String email = "user@gmail.com";
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var oAuth2SignUpRequest = MemberLoginSteps.oAuth2SignUpRequest(
                MemberLoginSteps.CLIENT_NAME, MemberLoginSteps.CLIENT_ID, authClientName, email, roasterSn);
        MemberLoginSteps.requestOAuth2SignUp(oAuth2SignUpRequest);

        // requestMyPage
        final var myPageRequest = MemberLoginSteps.myPageRequest(authClientName, email);
        final var myPageResponse = MemberLoginSteps.requestMyPage(myPageRequest);
        Assertions.assertEquals(HttpStatus.OK.value(), myPageResponse.statusCode());
        Assertions.assertEquals("1", myPageResponse.jsonPath().getString("data.id"));

        // requestUploadRoastingRecord
        final var roastingRecordRequest = RoastingRecordSteps.getRoastingRecordRequest();
        RoastingRecordSteps.requestUploadRoastingRecord(roastingRecordRequest);

        final var roastingRecordRequest2 = RoastingRecordSteps.getRoastingRecordRequest();
        RoastingRecordSteps.requestUploadRoastingRecord(roastingRecordRequest2);

        // requestRoastingRecordList
        final var request = RoastingRecordSteps.getRoastingRecordListRequest(authClientName, email);

        final var response = RoastingRecordSteps.requestRoastingRecordList(request);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("2", response.jsonPath().getString("count"));
        Assertions.assertEquals("1", response.jsonPath().getString("data[0].id"));
        Assertions.assertEquals("test roasting222", response.jsonPath().getString("data[0].title"));
        Assertions.assertEquals("2", response.jsonPath().getString("data[1].id"));
        Assertions.assertEquals("test roasting222", response.jsonPath().getString("data[1].title"));
    }
}
/**
 * * - 프로파일 이름
 *      * - 개수
 *      * - 날짜, 시간 (2024.2.14)
 *      * - 프로파일 아이디 (09:09:38)
 */