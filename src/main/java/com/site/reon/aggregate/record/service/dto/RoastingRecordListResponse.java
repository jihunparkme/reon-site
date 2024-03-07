package com.site.reon.aggregate.record.service.dto;

import com.site.reon.aggregate.record.domain.RoastingRecord;
import lombok.Builder;

public record RoastingRecordListResponse(
        long id,
        String title,
        String createdDt
) {
    @Builder
    public RoastingRecordListResponse {
    }

    public static RoastingRecordListResponse of(final RoastingRecord record) {
        return RoastingRecordListResponse.builder()
                .id(record.getId())
                .title(record.getTitle())
                .createdDt(record.getCreatedDt().toString())
                .build();
    }
}