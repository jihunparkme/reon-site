package com.site.reon.aggregate.member.infra.service;

import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.util.infra.RedisUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2WithdrawalService {
    private final RedisUtilService redisUtilService;

    public void signalWithdrawal(final WithdrawRequest request) {
        redisUtilService.setValueExpire(generateKey(request), "true", 180L);
    }

    private String generateKey(final WithdrawRequest request) {
        return KeyPrefix.WITHDRAW.prefix() + request.getAuthClientName() + ":" + request.getEmail();
    }
}
