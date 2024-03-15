package com.site.reon.global.event;

import com.site.reon.global.event.dto.SendMailEvent;
import com.site.reon.global.event.service.SendMailEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailEventHandler {
    private final SendMailEventService sendMailEventService;

    @Async
    @EventListener(SendMailEvent.class)
    public void handle(SendMailEvent event) {
        sendMailEventService.sendMail(event);
    }
}
