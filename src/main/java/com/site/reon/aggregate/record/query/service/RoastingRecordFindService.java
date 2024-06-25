package com.site.reon.aggregate.record.query.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.dto.RoastingRecordsResponse;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import com.site.reon.aggregate.record.query.dto.RecordSearchRequestParam;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.dto.api.RoastingRecordsAndPilotsResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoastingRecordFindService {

    Page<RoastingRecord> findAllByMemberIdOrderByIdDescPaging(long memberId, int page, int size);

    Page<RoastingRecord> findAllOrderByIdDescPaging(int page, int size);

    Page<RoastingRecord> findAllByFilter(long memberId, RecordSearchRequestParam param);

    Page<RoastingRecordsResponse> findAllByAdminFilter(AdminRecordSearchRequestParam param);

    RoastingRecordResponse findRoastingRecordBy(Long id);

    RoastingRecord findRoastingRecordBy(long recordId, long memberId);

    RoastingRecord findRoastingRecordBy(long recordId, long memberId, boolean pilot);

    List<RoastingRecordListResponse> findRoastingRecordListBy(long memberId);

    RoastingRecordsAndPilotsResponse findRoastingRecordsAndPilotsBy(Long memberId);
}
