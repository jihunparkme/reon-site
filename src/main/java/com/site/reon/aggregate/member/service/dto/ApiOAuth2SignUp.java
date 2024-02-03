package com.site.reon.aggregate.member.service.dto;

import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.global.common.annotation.ClientIdConstraint;
import com.site.reon.global.common.annotation.ClientNameConstraint;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiOAuth2SignUp {
    @ClientIdConstraint
    private String clientId;
    @ClientNameConstraint
    private String clientName;

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
                .firstName(this.firstName)
                .lastName(StringUtils.EMPTY)
                .email(this.email)
                .password(UUID.randomUUID().toString())
                .picture(this.picture)
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .oAuthClient(OAuth2Client.of(this.authClientName.toLowerCase()))
                .roasterSn(this.roasterSn)
                .activated(true)
                .build();
    }
}
