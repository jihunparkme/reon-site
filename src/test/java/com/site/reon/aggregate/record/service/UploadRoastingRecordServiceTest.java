package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.service.UploadRoastingRecordService;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.service.FindRoastingRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UploadRoastingRecordServiceTest {

    @Autowired
    FindRoastingRecordService findService;

    @Autowired
    UploadRoastingRecordService uploadService;

    @Test
    void upload() throws Exception {
        String title = "title";
        String fan = "fan";
        String heater = "heater";
        String temp1 = "[0,1,2]";
        String temp2 = "[0,1,2]";
        String temp3 = "[0,1,2]";
        String temp4 = "[0,1,2]";
        String ror = "RoR";
        String roasterSn = "roasterSn";
        RoastingRecordRequest request = new RoastingRecordRequest(title, fan, heater, temp1, temp2, temp3, temp4, ror, roasterSn, 100L);
        
        uploadService.upload(request);

        final List<RoastingRecordListResponse> roastingRecords = findService.findRoastingRecordListBy(100L);
        final RoastingRecordListResponse result = roastingRecords.get(0);
        assertEquals("title", result.title());
    }
}