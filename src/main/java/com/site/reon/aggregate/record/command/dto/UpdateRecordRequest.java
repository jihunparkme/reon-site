package com.site.reon.aggregate.record.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecordRequest {
    @NotNull(message = "memberId is required.")
    private Long memberId;
    @NotNull(message = "memo is required.")
    private String memo;
}
