package com.site.reon.aggregate.member.command.domain;

import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2 {
    @Column(length = 2000)
    private String picture;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private OAuth2Client oAuthClient;

    private Long oauthUserId;

    public void update(final String picture, final Long oAuthUserId) {
        this.picture = picture;
        this.oauthUserId = oAuthUserId;
    }
}
