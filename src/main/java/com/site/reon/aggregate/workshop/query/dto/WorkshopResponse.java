package com.site.reon.aggregate.workshop.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkshopResponse {
    private Long id;
    private String title;
    private String content;
    private Long recordId;

    public static WorkshopResponse create(final long recordId) {
        return WorkshopResponse.builder()
                .recordId(recordId)
                .build();
    }
}
