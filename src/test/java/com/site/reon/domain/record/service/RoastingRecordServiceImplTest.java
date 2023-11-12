package com.site.reon.domain.record.service;

import com.site.reon.domain.record.dto.RoastingRecordRequest;
import com.site.reon.domain.record.dto.RoastingRecordResponse;
import com.site.reon.domain.record.entity.RoastingRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class RoastingRecordServiceImplTest {

    @Autowired
    RoastingRecordService service;

    @Test
    void upload() throws Exception {
        String title = "title";
        String fan = "fan";
        String heater = "heater";
        String temp1 = "temp1";
        String temp2 = "temp2";
        String temp3 = "temp3";
        String temp4 = "temp4";
        String roasterSn = "roasterSn";
        RoastingRecordRequest request = new RoastingRecordRequest(title, fan, heater, temp1, temp2, temp3, temp4, roasterSn);
        service.upload(request);

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