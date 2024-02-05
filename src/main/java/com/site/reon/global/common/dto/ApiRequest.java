package com.site.reon.global.common.dto;

import com.site.reon.global.common.annotation.ClientIdConstraint;
import com.site.reon.global.common.annotation.ClientNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest {
    @ClientIdConstraint
    private String clientId;
    @ClientNameConstraint
    private String clientName;
}
