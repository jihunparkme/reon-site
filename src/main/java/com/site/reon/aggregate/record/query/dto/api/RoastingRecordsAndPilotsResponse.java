package com.site.reon.aggregate.record.query.dto.api;

import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import lombok.Builder;

import java.util.List;

public record RoastingRecordsAndPilotsResponse(
        List<RoastingRecordListResponse> personalRecords,
        List<RoastingRecordListResponse> pilotRecords
) {
    @Builder
    public RoastingRecordsAndPilotsResponse {
    }
}
