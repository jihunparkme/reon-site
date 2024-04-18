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
        String title = "Inquiry has been registered.";
        String contents = "Hello. This is message.";

        Map<String, String> contentsMap = new LinkedHashMap<>();
        contentsMap.put("Name", "Aaron");
        contentsMap.put("Email", "abc@gmail.com");
        contentsMap.put("Subject", "This is title.");
        contentsMap.put("Message", contents.replaceAll("(\r\n|\n)", "<br/>"));
        String template = MailTemplate.generateContents(title, contentsMap);

        System.out.println(template);
    }
}