package com.site.reon.global.event.service;

import com.site.reon.aggregate.member.command.service.MemberCommandService;
import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.global.common.util.infra.RedisUtilService;
import com.site.reon.global.event.dto.GoogleOauth2TokenRevokeEvent;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOauth2TokenRevokeEventService {

    @Value("${oauth2.client.google.revoke-url}")
    private String revokeUrl;

    private final WebClient webClient;
    private final RedisUtilService redisUtilService;
    private final MemberCommandService memberCommandService;

    public void revoke(final GoogleOauth2TokenRevokeEvent event) {
        final Mono<String> responseMono = webClient.mutate()
                .baseUrl(revokeUrl)
                .build()
                .get().uri(uriBuilder -> uriBuilder
                        .queryParam("token", event.getAccessToken())
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        clientResponse -> Mono.error(new RuntimeException("Client error")))
                .onStatus(
                        status -> status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Server error")))
                .bodyToMono(String.class);

        responseMono.subscribe(
                content -> {
                    log.info("GoogleOauth2TokenRevokeEventService.revoke api call success. {}", content);
                    final WithdrawRequest build = WithdrawRequest.builder()
                            .email(event.getEmail())
                            .authClientName(OAuth2Client.GOOGLE.name())
                            .build();
                    redisUtilService.deleteOf(event.getSignalKey());
                    memberCommandService.withdraw(build);
                },
                error -> log.error("GoogleOauth2TokenRevokeEventService.revoke api call error. response status: {}", error.getMessage())
        );
    }
}
