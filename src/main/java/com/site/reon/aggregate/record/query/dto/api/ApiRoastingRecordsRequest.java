package com.site.reon.aggregate.record.query.dto.api;

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
public class ApiRoastingRecordsRequest extends ApiRequest {
    @NotNull(message = "memberId is required.")
    private Long memberId;

    private boolean pilot;
}
