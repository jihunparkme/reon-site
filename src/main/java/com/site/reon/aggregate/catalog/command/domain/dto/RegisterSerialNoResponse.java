package com.site.reon.aggregate.catalog.command.domain.dto;

import lombok.Builder;

import java.util.List;

public record RegisterSerialNoResponse(
        List<SerialNoRegisterResult> registerResult
) {
    @Builder
    public RegisterSerialNoResponse {
    }
}
