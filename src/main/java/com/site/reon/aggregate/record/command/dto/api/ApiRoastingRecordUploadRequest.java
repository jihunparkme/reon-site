package com.site.reon.aggregate.record.command.dto.api;

import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.global.common.annotation.ClientIdConstraint;
import com.site.reon.global.common.annotation.ClientNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRoastingRecordUploadRequest extends RoastingRecordRequest {
    @ClientIdConstraint
    private String clientId;
    @ClientNameConstraint
    private String clientName;
}
