package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import com.site.reon.global.common.config.QueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDslConfig.class)
class RoastingRecordAdminRepositoryCustomImplTest {

    @Autowired
    private RoastingRecordRepository roastingRecordRepository;

    @Test
    void when_findAllByAdminFilter_then_return_all_record() throws Exception {
        final AdminRecordSearchRequestParam param = new AdminRecordSearchRequestParam(0, 10, "", "", "", "", "");

        final Page<RoastingRecord> result = roastingRecordRepository.findAllByAdminFilter(param);

        assertEquals(13, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }

    @Test
    void when_findAllByAdminFilter_then_return_title_filtered_record() throws Exception {
        final AdminRecordSearchRequestParam param = new AdminRecordSearchRequestParam(0, 10, "10", "", "", "", "");

        final Page<RoastingRecord> result = roastingRecordRepository.findAllByAdminFilter(param);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByAdminFilter_then_return_serialNo_filtered_record() throws Exception {
        final AdminRecordSearchRequestParam param = new AdminRecordSearchRequestParam(0, 10, "", "ASGFDSAGASABCD", "", "", "");

        final Page<RoastingRecord> result = roastingRecordRepository.findAllByAdminFilter(param);

        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByAdminFilter_then_return_email_filtered_record() throws Exception {
        final AdminRecordSearchRequestParam param = new AdminRecordSearchRequestParam(0, 10, "", "", "pilot@gmail.com", "", "");

        final Page<RoastingRecord> result = roastingRecordRepository.findAllByAdminFilter(param);

        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByAdminFilter_then_return_create_date_filtered_record() throws Exception {
        final AdminRecordSearchRequestParam param = new AdminRecordSearchRequestParam(0, 10, "", "", "", "2023-10-01", "2023-10-05");

        final Page<RoastingRecord> result = roastingRecordRepository.findAllByAdminFilter(param);

        assertEquals(5, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByAdminFilter_then_return_title_and_create_date_filtered_record() throws Exception {
        final AdminRecordSearchRequestParam param = new AdminRecordSearchRequestParam(0, 10, "0", "", "", "2023-10-01", "2023-10-05");

        final Page<RoastingRecord> result = roastingRecordRepository.findAllByAdminFilter(param);

        assertEquals(4, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }
}