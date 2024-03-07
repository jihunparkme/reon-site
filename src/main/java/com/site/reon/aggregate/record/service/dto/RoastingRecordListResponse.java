package com.site.reon.aggregate.record.service.dto;

public record RoastingRecordListResponse(
        long profileId,
        String profileName,
        String createDt
) {
}