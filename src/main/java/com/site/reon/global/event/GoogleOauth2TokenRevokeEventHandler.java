package com.site.reon.global.event;

import com.site.reon.global.event.dto.GoogleOauth2TokenRevokeEvent;
import com.site.reon.global.event.service.GoogleOauth2TokenRevokeEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleOauth2TokenRevokeEventHandler {

    private final GoogleOauth2TokenRevokeEventService googleOauth2TokenRevokeEventService;

    @Async
    @EventListener(GoogleOauth2TokenRevokeEvent.class)
    public void handle(GoogleOauth2TokenRevokeEvent event) {
        googleOauth2TokenRevokeEventService.revoke(event);
    }
}
