package com.site.reon.aggregate.member.infra.kakao.dto;

import io.jsonwebtoken.lang.Assert;
import lombok.Builder;

public record KakaoOauth2UnlinkRequest(String targetIdType, long targetId) {
    @Builder
    public KakaoOauth2UnlinkRequest {
        Assert.hasText(targetIdType, "targetIdType is required.");
        Assert.notNull(targetId, "targetId is required.");
        Assert.isTrue(targetId > 0, "targetId must be greater than 0.");
    }
}
