package com.site.reon.aggregate.index.service;

import com.site.reon.aggregate.index.dto.ContactMailRequest;
import com.site.reon.global.common.constant.mail.MailSubject;
import com.site.reon.global.common.event.Events;
import com.site.reon.global.common.util.mail.service.MailTemplate;
import com.site.reon.global.event.dto.SendMailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {
    @Value("${admin.mail.address}")
    private String adminAddress;

    public void contactMail(final ContactMailRequest request) {
        Map<String, String> contentsMap = new LinkedHashMap<>();
        contentsMap.put("Name", request.getName());
        contentsMap.put("Email", request.getEmail());
        contentsMap.put("Subject", request.getSubject());
        contentsMap.put("Message", request.getMessage().replaceAll("(\r\n|\n)", "<br/>"));

        log.info("[Event] send Contact Mail Event. purpose: {}, email: {}", MailSubject.INQUIRY, request.getEmail());
        Events.raise(SendMailEvent.builder()
                .subject(MailSubject.INQUIRY.title())
                .contents(MailTemplate.generateContents(MailSubject.INQUIRY.title(), contentsMap))
                .addressList(Optional.of(Arrays.asList(adminAddress, request.getEmail())))
                .build());
    }
}
