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
            throw new IllegalArgumentException("The time limit for entering the authentication code has been exceeded. Please try again.");
        }

        if (!authCode.equals(authCodeOpt.get())) {
            throw new IllegalArgumentException("The authentication code is incorrect. Please enter it again.");
        }

        return true;
    }
}
