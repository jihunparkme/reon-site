package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.RoastingRecordSteps;
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
}