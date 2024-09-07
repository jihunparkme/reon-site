package com.site.reon.aggregate.workshop.query.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkshopsResponse(
        Long id,
        String title,
        LocalDateTime createdDt,
        String email,
        String firstName,
        String lastName
) {
}
