package com.site.reon.global.common.util.mail.service;

import com.site.reon.global.common.event.Events;
import com.site.reon.global.common.util.mail.dto.SendMailEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Disabled
@SpringBootTest
class MailUtilServiceEventTest {

    @Test
    void sendAuthCodeMailEvent() throws Exception {
        String subject = "TEST: 인증번호 발송";
        String purpose = "회원가입";
        String authCode = "111111";
        Events.raise(SendMailEvent.builder()
                .subject(subject)
                .contents(MailTemplate.generateAuthCodeTemplate(purpose, authCode))
                .addressList(Optional.of(Arrays.asList("contact@myreon.net")))
                .build());
    }

    @Test
    void sendContentsEvent() throws Exception {
        String subject = "TEST: 문의 등록 안내";
        String title = "문의 등록 안내";
        String contents = "안녕하세요. 내용입니다.";

        Map<String, String> contentsMap = new LinkedHashMap<>();
        contentsMap.put("이름", "Aaron");
        contentsMap.put("휴대폰 번호", "010-1111-1111");
        contentsMap.put("이메일", "abc@gmail.com");
        contentsMap.put("제목", "제목입니다.");
        contentsMap.put("내용", contents.replaceAll("(\r\n|\n)", "<br/>"));

        Events.raise(SendMailEvent.builder()
                .subject(subject)
                .contents(MailTemplate.generateContents(title, contentsMap))
                .addressList(Optional.of(Arrays.asList("contact@myreon.net")))
                .build());
    }
}