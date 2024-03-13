package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.WithdrawRequest;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.util.infra.RedisUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberGoogleOauth2LoginService {
    private final RedisUtilService redisUtilService;

    public void withdraw(final WithdrawRequest request) {
        redisUtilService.setValueExpire(generateKey(request), "true", 180L);
    }

    private String generateKey(final WithdrawRequest request) {
        return KeyPrefix.WITHDRAW.prefix() + request.getAuthClientName() + ":" + request.getEmail();
    }
}
