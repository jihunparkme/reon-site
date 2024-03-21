package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.service.UploadRoastingRecordService;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.service.FindRoastingRecordService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class FindRoastingRecordServiceTest {

    @Autowired
    FindRoastingRecordService findService;

    @Autowired
    UploadRoastingRecordService uploadService;

    @Test
    @Disabled
    void findAllSortByIdDescPaging() throws Exception {
        Page<RoastingRecord> recordPage = findService.findAllSortByIdDescPaging(2, 0, 10);

        assertEquals(11, recordPage.getTotalElements());
        assertEquals(2, recordPage.getTotalPages());
        assertEquals(0, recordPage.getNumber());
        assertEquals(10,recordPage.getNumberOfElements());
        assertEquals(10, recordPage.getSize());
    }

    @Test
    void findRoastingRecordById() throws Exception {
        String title = "test roasting";
        String fan = "[90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,105,105,105,105,105,105,105,105]";
        String heater = "[100,105,125,130,130,130,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,125,135,155,155,155,155]";
        String temp1 = "[0,1,2]";
        String temp2 = "[0,1,2]";
        String temp3 = "[0,1,2]";
        String temp4 = "[0,1,2]";
        String ror = "RoR";
        String roasterSn = "roasterSn";
        RoastingRecordRequest request = new RoastingRecordRequest(title, fan, heater, temp1, temp2, temp3, temp4, ror, roasterSn, 101L);
        uploadService.upload(request);

        final List<RoastingRecordListResponse> roastingRecords = findService.findRoastingRecordListBy(101L);

        final RoastingRecordListResponse result = roastingRecords.get(0);
        assertEquals("test roasting", result.title());
    }

    @Test
    void findRoastingRecordById_should_return_empty() throws Exception {
        RoastingRecordResponse result = findService.findRoastingRecordBy(10000L);

        assertEquals(RoastingRecordResponse.EMPTY, result);
    }
}