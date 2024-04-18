package com.site.reon.global.security.dto;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.query.dto.AuthorityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Set<AuthorityDto> authorityDtoSet;

    public static SessionMember from(Member member) {
        if (member == null) {
            return null;
        }

        return SessionMember.builder()
                .id(member.getId())
                .name(member.getPersonalInfo().getFirstName())
                .email(member.getEmail())
                .picture(member.getOAuth2().getPicture())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder()
                                .authorityName(authority.getAuthorityName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}