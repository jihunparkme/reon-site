package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.command.service.UploadRoastingRecordService;
import com.site.reon.aggregate.record.query.service.FindRoastingRecordService;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class RoastingRecordServiceImplTest {

    @Autowired
    FindRoastingRecordService service;

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
        RoastingRecordRequest request = new RoastingRecordRequest(title, fan, heater, temp1, temp2, temp3, temp4, ror, roasterSn);
        uploadService.upload(request);

        RoastingRecordResponse result = service.findRoastingRecordBy(roasterSn);

        assertEquals("title", result.getTitle());
        assertEquals("fan", result.getFan());
        assertEquals("heater", result.getHeater());
    }

    @Test
    void findAllSortByIdDescPaging() throws Exception {
        Page<RoastingRecord> recordPage = service.findAllSortByIdDescPaging(0, 10);

        assertEquals(11, recordPage.getTotalElements());
        assertEquals(2, recordPage.getTotalPages());
        assertEquals(0, recordPage.getNumber());
        assertEquals(10,recordPage.getNumberOfElements());
        assertEquals(10, recordPage.getSize());
    }

    @Test
    void findRoastingRecordById() throws Exception {
        RoastingRecordResponse result = service.findRoastingRecordBy(1L);

        assertEquals("test roasting", result.getTitle());
        assertEquals("[90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,105,105,105,105,105,105,105,105]",
                result.getFan());
        assertEquals("[100,105,125,130,130,130,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,125,135,155,155,155,155]",
                result.getHeater());
    }

    @Test
    void findRoastingRecordById_should_return_empty() throws Exception {
        RoastingRecordResponse result = service.findRoastingRecordBy(10000L);

        assertEquals(RoastingRecordResponse.EMPTY, result);
    }

    @Test
    void findRoastingRecordByRoasterSn() throws Exception {
        RoastingRecordResponse result = service.findRoastingRecordBy("AGBAG1241AFWADF");

        assertEquals("test roasting", result.getTitle());
        assertEquals("[90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,105,105,105,105,105,105,105,105]",
                result.getFan());
        assertEquals("[100,105,125,130,130,130,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,125,135,155,155,155,155]",
                result.getHeater());
    }

    @Test
    void findRoastingRecordByRoasterSn_should_return_empty() throws Exception {
        RoastingRecordResponse result = service.findRoastingRecordBy("XXX");

        assertEquals(RoastingRecordResponse.EMPTY, result);
    }
}