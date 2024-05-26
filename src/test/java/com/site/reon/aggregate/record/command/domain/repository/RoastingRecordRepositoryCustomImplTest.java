package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.RecordSearchRequestParam;
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
class RoastingRecordRepositoryCustomImplTest {

    @Autowired
    private RoastingRecordRepository roastingRecordRepository;

    @Test
    void when_findByFilter_then_return_all_record() throws Exception {
        final RecordSearchRequestParam param = new RecordSearchRequestParam(0, 10, "", "", "");

        final Page<RoastingRecord> result = roastingRecordRepository.findByFilter(2L, param);

        assertEquals(11, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }

    @Test
    void when_findByFilter_then_return_title_filtered_record() throws Exception {
        final RecordSearchRequestParam param = new RecordSearchRequestParam(0, 10, "10", "", "");

        final Page<RoastingRecord> result = roastingRecordRepository.findByFilter(2L, param);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findByFilter_then_return_create_date_filtered_record() throws Exception {
        final RecordSearchRequestParam param = new RecordSearchRequestParam(0, 10, "", "2023-10-01", "2023-10-05");

        final Page<RoastingRecord> result = roastingRecordRepository.findByFilter(2L, param);

        assertEquals(5, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }
}