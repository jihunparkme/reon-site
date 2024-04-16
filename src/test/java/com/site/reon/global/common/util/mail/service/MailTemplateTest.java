package com.site.reon.global.common.util.mail.service;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class MailTemplateTest {

    @Test
    void generateAuthCodeTemplate() throws Exception {
        String purpose = "sign up";
        String authCode = "111111";
        String template = MailTemplate.generateAuthCodeTemplate(purpose, authCode);

        System.out.println(template);
    }

    @Test
    void generateContents() throws Exception {
        String title = "문의 등록 안내";
        String contents = "안녕하세요. 내용입니다.";

        Map<String, String> contentsMap = new LinkedHashMap<>();
        contentsMap.put("이름", "Aaron");
        contentsMap.put("휴대폰 번호", "010-1111-1111");
        contentsMap.put("이메일", "abc@gmail.com");
        contentsMap.put("제목", "제목입니다.");
        contentsMap.put("내용", contents.replaceAll("(\r\n|\n)", "<br/>"));
        String template = MailTemplate.generateContents(title, contentsMap);

        System.out.println(template);
    }
}