package com.site.reon.aggregate.record.query.dto;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record RoastingRecordListResponse(
        long id,
        String title,
        String createdDate,
        String createdTime
) {
    @Builder
    public RoastingRecordListResponse {
    }

    public static RoastingRecordListResponse of(final RoastingRecord record) {
        LocalDateTime dateTime = LocalDateTime.parse(record.getCreatedDt().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return RoastingRecordListResponse.builder()
                .id(record.getId())
                .title(record.getRoastingInfo().getTitle())
                .createdDate(dateTime.toLocalDate().toString())
                .createdTime(dateTime.toLocalTime().withNano(0).toString())
                .build();
    }
}