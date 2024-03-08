package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.service.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.service.dto.RoastingRecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FindRoastingRecordService {

    Page<RoastingRecord> findAllSortByIdDescPaging(int page, int size);

    RoastingRecordResponse findRoastingRecordBy(Long id);

    RoastingRecordResponse findRoastingRecordBy(String roasterSn);

    List<RoastingRecordListResponse> findRoastingRecordListBy(String email, String authClientName);
}
