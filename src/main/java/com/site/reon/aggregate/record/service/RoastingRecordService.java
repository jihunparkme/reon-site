package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.entity.RoastingRecord;
import org.springframework.data.domain.Page;

public interface RoastingRecordService {
    void upload(RoastingRecordRequest request);

    Page<RoastingRecord> findAllSortByIdDescPaging(int page, int size);

    RoastingRecordResponse findRoastingRecordBy(Long id);

    RoastingRecordResponse findRoastingRecordBy(String roasterSn);
}
