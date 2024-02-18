package com.site.reon.global.common.util.mail.handler;

import com.site.reon.global.common.util.mail.dto.SendMailEvent;
import com.site.reon.global.common.util.mail.service.MailUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailEventHandler {
    private final MailUtilService mailUtilService;

    @Async
    @EventListener(SendMailEvent.class)
    public void handle(SendMailEvent event) {
        mailUtilService.sendMail(event);
    }
}
