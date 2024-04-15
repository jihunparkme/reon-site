package com.site.reon.aggregate.member.command.dto;

import com.site.reon.aggregate.member.command.domain.*;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.common.dto.ApiRequest;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ApiOAuth2SignUpRequest extends ApiRequest {
    @NotBlank(message = "roasterSn is required.")
    private String roasterSn;

    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "firstName is required.")
    private String firstName;

    @NotBlank(message = "picture is required.")
    private String picture;

    @NotBlank(message = "authClientName is required.")
    private String authClientName;

    public Member toMember() {
        return Member.builder()
                .email(this.email)
                .password(UUID.randomUUID().toString())
                .personalInfo(PersonalInfo.builder()
                        .firstName(this.firstName)
                        .lastName(StringUtils.EMPTY)
                        .build())
                .oAuth2(OAuth2.builder()
                        .picture(this.picture)
                        .oAuthClient(OAuth2Client.of(this.authClientName.toLowerCase()))
                        .build())
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .productInfo(ProductInfo.builder()
                        .roasterSn(this.roasterSn)
                        .build())
                .activated(true)
                .build();
    }
}
