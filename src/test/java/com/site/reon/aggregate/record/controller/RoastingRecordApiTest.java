package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.member.controller.steps.MemberLoginApiSteps;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.global.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoastingRecordApiTest extends ApiTest {

    private final static String AUTH_CLIENT_NAME = "kakao";
    private final static String EMAIL = "user@gmail.com";

    /**
     * /api/records
     */
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

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals("2", response.jsonPath().getString("count"));
        assertEquals("1", response.jsonPath().getString("data[0].id"));
        assertEquals("test roasting222", response.jsonPath().getString("data[0].title"));
        assertEquals("2", response.jsonPath().getString("data[1].id"));
        assertEquals("test roasting222", response.jsonPath().getString("data[1].title"));
    }

    /**
     * /api/records/contain/pilot
     */
    @Test
    void find_roasting_record_and_pilot_list() {
        // given
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        final long memberId = getMemberId(AUTH_CLIENT_NAME, EMAIL);
        requestUploadRoastingRecord();
        requestUploadRoastingRecord();

        // when
        final var request = RoastingRecordSteps.getRoastingRecordsAndPilotsRequest(memberId);

        // then
        final var response = RoastingRecordSteps.requestRoastingRecordsAndPilots(request);

        System.out.println(response.jsonPath().getString("data.personalRecords"));
        System.out.println(response.jsonPath().getString("data.personalRecords"));

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals("1", response.jsonPath().getString("count"));

        List<RoastingRecordListResponse> personalRecords = response.jsonPath().getList("data.personalRecords");
        List<RoastingRecordListResponse> pilotRecords = response.jsonPath().getList("data.pilotRecords");
        assertEquals(2, personalRecords.size());
        assertEquals(0, pilotRecords.size());
    }

    /**
     * /api/records/ + recordId
     */
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

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals("1", response.jsonPath().getString("data.id"));
        assertEquals("test roasting222", response.jsonPath().getString("data.title"));
    }

    /**
     * /record/upload
     */
    @Test
    void upload_record_asis() {
        final var request = RoastingRecordSteps.getRoastingRecordRequest();

        final var response = RoastingRecordSteps.requestUploadRoastingRecord(request);

        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    /**
     * /api/records/upload
     */
    @Test
    void upload_record() {
        final var request = RoastingRecordSteps.getApiRoastingRecordRequest();

        final var response = RoastingRecordSteps.requestApiUploadRoastingRecord(request);

        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    /**
     * /api/records/ + recordId + /share?email= + email
     */
    @Test
    void share_record() {
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        requestUploadRoastingRecord();
        final var request = RoastingRecordSteps.getApiRoastingRecordShareRequest();

        final var response = RoastingRecordSteps.requestApiShareRoastingRecord(request, "1", "user@gmail.com");

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals("1", response.jsonPath().getString("count"));
    }

    @Test
    void share_record_then_not_found_record() {
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        requestUploadRoastingRecord();
        final var request = RoastingRecordSteps.getApiRoastingRecordShareRequest();

        final var response = RoastingRecordSteps.requestApiShareRoastingRecord(request, "9999", "user@gmail.com");

        assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode());
        assertEquals("0", response.jsonPath().getString("count"));
        assertEquals("Not Found Roasting Record", response.jsonPath().getString("message"));
    }

    @Test
    void share_record_then_not_found_share_owner_member() {
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        requestUploadRoastingRecord();
        final var request = RoastingRecordSteps.getApiRoastingRecordShareRequest();

        final var response = RoastingRecordSteps.requestApiShareRoastingRecord(request, "1", "user9999@gmail.com");

        assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode());
        assertEquals("0", response.jsonPath().getString("count"));
        assertEquals("Not Found Roasting Record Owner Member", response.jsonPath().getString("message"));
    }

    @Test
    void share_record_then_request_incorrect() {
        final String tempEmail = "user9999@gmail.com";
        requestOAuth2SignUp(AUTH_CLIENT_NAME, EMAIL);
        requestOAuth2SignUp(AUTH_CLIENT_NAME, tempEmail);
        requestUploadRoastingRecord();
        final var request = RoastingRecordSteps.getApiRoastingRecordShareRequest();

        final var response = RoastingRecordSteps.requestApiShareRoastingRecord(request, "1", tempEmail);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals("0", response.jsonPath().getString("count"));
        assertEquals("Roasting information is incorrect.", response.jsonPath().getString("message"));
    }

    private void requestUploadRoastingRecord() {
        final var roastingRecordRequest = RoastingRecordSteps.getRoastingRecordRequest();

        RoastingRecordSteps.requestUploadRoastingRecord(roastingRecordRequest);
    }

    private long getMemberId(final String authClientName, final String email) {
        final var myPageRequest = MemberLoginApiSteps.myPageRequest(authClientName, email);
        final var myPageResponse = MemberLoginApiSteps.requestMyPage(myPageRequest);
        assertEquals(HttpStatus.OK.value(), myPageResponse.statusCode());

        final String memberId = myPageResponse.jsonPath().getString("data.id");
        assertEquals("1", memberId);

        return Long.parseLong(memberId);
    }

    private void requestOAuth2SignUp(final String authClientName, final String email) {
        final String roasterSn = "AFSFE-ASDVES-AbdSc-AebsC";
        final var oAuth2SignUpRequest = MemberLoginApiSteps.oAuth2SignUpRequest(
                MemberLoginApiSteps.CLIENT_NAME, MemberLoginApiSteps.CLIENT_ID, authClientName, email, roasterSn);

        MemberLoginApiSteps.requestOAuth2SignUp(oAuth2SignUpRequest);
    }
}