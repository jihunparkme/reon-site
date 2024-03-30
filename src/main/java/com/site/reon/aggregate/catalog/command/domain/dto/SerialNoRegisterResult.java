package com.site.reon.aggregate.catalog.command.domain.dto;

import lombok.Builder;

public record SerialNoRegisterResult(
        String serialNo,
        boolean result
) {
    @Builder
    public SerialNoRegisterResult {
    }
}