package com.site.reon.global.common.util.mail.handler;

import com.site.reon.global.common.util.mail.dto.SendAuthCodeMailEvent;
import com.site.reon.global.common.util.mail.service.MailUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendAuthCodeMailEventHandler {
    private final MailUtilService mailUtilService;

    @Async
    @EventListener(SendAuthCodeMailEvent.class)
    public void handle(SendAuthCodeMailEvent event) {
        mailUtilService.sendMail(event);
    }
}
