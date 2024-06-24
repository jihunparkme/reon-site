package com.site.reon.aggregate.member.query.dto;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.global.common.constant.member.MemberType;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private long id;
    private MemberType type;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String prdCode;
    private String roasterSn;
    private boolean activated;
    private Set<AuthorityDto> authorityDtoSet;
    private String companyName;
    private String address;
    private String picture;
    private OAuth2Client oAuthClient;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDt;

    public static MemberResponse from(Member member) {
        if (member == null) {
            return null;
        }

        return MemberResponse.builder()
                .id(member.getId())
                .type(member.getType())
                .email(member.getEmail())
                .firstName(member.getPersonalInfo().getFirstName())
                .lastName(member.getPersonalInfo().getLastName())
                .phone(member.getPersonalInfo().getPhone())
                .companyName(member.getPersonalInfo().getCompanyName())
                .address(member.getPersonalInfo().getAddress())
                .prdCode(member.getProductInfo().getPrdCode())
                .roasterSn(member.getProductInfo().getRoasterSn())
                .activated(member.isActivated())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder()
                                .authorityName(authority.getAuthorityName())
                                .build())
                        .collect(Collectors.toSet()))
                .picture(member.getOAuth2().getPicture())
                .oAuthClient(member.getOAuth2().getOAuthClient())
                .createdDt(member.getCreatedDt())
                .build();
    }
}
