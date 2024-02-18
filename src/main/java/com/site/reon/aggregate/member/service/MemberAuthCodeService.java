package com.site.reon.aggregate.member.service;

import com.site.reon.global.common.constant.mail.MailSubject;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.event.Events;
import com.site.reon.global.common.util.AuthCodeUtil;
import com.site.reon.global.common.util.infra.RedisUtilService;
import com.site.reon.global.common.util.mail.dto.SendMailEvent;
import com.site.reon.global.common.util.mail.service.MailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAuthCodeService {
    private final RedisUtilService redisUtilService;

    public void sendAuthenticationCodeByEmail(KeyPrefix keyPrefix, String purpose, String email) {
        String authCode = AuthCodeUtil.generateAuthCodeString();
        redisUtilService.setValueExpire(keyPrefix.prefix() + email, authCode, 180L);

        log.info("[Event] send Auth Code Mail Event. purpose: {}, email: {}", purpose, email);
        Events.raise(SendMailEvent.builder()
                .subject(MailSubject.AUTH_CODE.title())
                .contents(MailTemplate.generateAuthCodeTemplate(purpose, authCode))
                .addressList(Optional.of(Arrays.asList(email)))
                .build());
    }

    public boolean verifyAuthenticationCode(KeyPrefix keyPrefix, String email, String authCode) {
        Optional<String> authCodeOpt = redisUtilService.getValueOf(keyPrefix.prefix() + email);
        if (authCodeOpt.isEmpty()) {
            throw new IllegalArgumentException("인증번호 입력 가능 시간을 초과하였습니다. 다시 시도해 주세요.");
        }

        if (!authCode.equals(authCodeOpt.get())) {
            throw new IllegalArgumentException("인증번호가 올바르지 않습니다. 다시 입력해 주세요.");
        }

        return true;
    }
}
