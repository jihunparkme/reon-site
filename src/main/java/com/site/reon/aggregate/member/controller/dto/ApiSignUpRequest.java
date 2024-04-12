package com.site.reon.aggregate.member.controller.dto;

import com.site.reon.aggregate.member.service.dto.SignUpDto;
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
public class ApiSignUpRequest extends SignUpDto {
    @ClientIdConstraint
    private String clientId;
    @ClientNameConstraint
    private String clientName;
}
