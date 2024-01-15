package com.site.reon.global.security.oauth2.dto;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.dto.AuthorityDto;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Set<AuthorityDto> authorities;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.name = member.getFirstName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
        this.authorities = member.getAuthorities().stream()
                .map(authority -> AuthorityDto.builder()
                        .authorityName(authority.getAuthorityName())
                        .build())
                .collect(Collectors.toSet());
    }
}