package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.service.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.service.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.domain.RoastingRecord;
import org.springframework.data.domain.Page;

public interface RoastingRecordService {
    void upload(RoastingRecordRequest request);

    Page<RoastingRecord> findAllSortByIdDescPaging(int page, int size);

    RoastingRecordResponse findRoastingRecordBy(Long id);

    RoastingRecordResponse findRoastingRecordBy(String roasterSn);
}
