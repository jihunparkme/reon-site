package com.site.reon.aggregate.member.infra.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KakaoOauth2Api {
    UNLINK("/v1/user/unlink", "카카오 계정 연결 끊기"),
    ;

    private final String uri;
    private final String name;
}
