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
        String record = "record";
        String roasterSn = "roasterSn";
        RoastingRecordRequest request = new RoastingRecordRequest(title, record, roasterSn);
        service.upload(request);

        RoastingRecordResponse result = service.findRoastingRecordBy(roasterSn);

        assertEquals("title", result.getTitle());
        assertEquals("record", result.getRecord());
    }

    @Test
    void findAllSortByIdDescPaging() throws Exception {
        Page<RoastingRecord> recordPage = service.findAllSortByIdDescPaging(0, 10);

        assertEquals(12, recordPage.getTotalElements());
        assertEquals(2, recordPage.getTotalPages());
        assertEquals(0, recordPage.getNumber());
        assertEquals(10,recordPage.getNumberOfElements());
        assertEquals(10, recordPage.getSize());
    }

    @Test
    void findRoastingRecordById() throws Exception {
        RoastingRecordResponse result = service.findRoastingRecordBy(1L);

        assertEquals("test roasting", result.getTitle());
        assertEquals("{\"fan\":[90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,105,105,105,105,105,105,105,105],\"power\":[100,105,125,130,130,130,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,125,135,155,155,155,155]}",
                result.getRecord());
    }

    @Test
    void findRoastingRecordByRoasterSn() throws Exception {
        RoastingRecordResponse result = service.findRoastingRecordBy("AGBAG1241AFWADF");

        assertEquals("test roasting", result.getTitle());
        assertEquals("{\"fan\":[90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,105,105,105,105,105,105,105,105],\"power\":[100,105,125,130,130,130,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120,125,135,155,155,155,155]}",
                result.getRecord());
    }
}