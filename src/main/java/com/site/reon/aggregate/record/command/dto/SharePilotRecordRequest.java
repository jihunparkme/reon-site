package com.site.reon.aggregate.record.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SharePilotRecordRequest {
    @NotNull(message = "pilot is required.")
    private Boolean pilot;
}
