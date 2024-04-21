package com.site.reon.aggregate.record.command.dto.api;

import com.site.reon.global.common.dto.ApiRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ApiDeleteRecordRequest extends ApiRequest {
    @NotNull(message = "memberId is required.")
    private Long memberId;
}