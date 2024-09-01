package com.site.reon.aggregate.workshop.query.dto;

import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.workshop.command.domain.Workshop;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkshopResponse(
        Long id,
        String title,
        String content,
        RoastingRecordResponse record,
        LocalDateTime createdDt
) {
    public static WorkshopResponse of(final Workshop workshop, final RoastingRecordResponse roastingRecord) {
        return WorkshopResponse.builder()
                .id(workshop.getId())
                .title(workshop.getTitle())
                .content(workshop.getContent())
                .record(roastingRecord)
                .createdDt(workshop.getCreatedDt())
                .build();
    }
}
