package com.site.reon.aggregate.record.query.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FindRoastingRecordService {

    Page<RoastingRecord> findAllSortByIdDescPaging(int page, int size);

    RoastingRecordResponse findRoastingRecordBy(Long id);

    ApiRoastingRecordResponse findRoastingRecordBy(long recordId, long memberId);

    List<RoastingRecordListResponse> findRoastingRecordListBy(long memberId);
}
