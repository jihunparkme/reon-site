package com.site.reon.aggregate.member.infra.kakao.service;

import com.site.reon.aggregate.member.infra.kakao.dto.KakaoOauth2Api;
import com.site.reon.aggregate.member.infra.kakao.dto.KakaoOauth2UnlinkRequest;
import com.site.reon.aggregate.member.infra.kakao.dto.KakaoOauth2UnlinkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauth2ApiService {

    private final static String TARGET_ID_TYPE = "user_id";

    @Value("${oauth2.client.kakao.admin-id}")
    private String adminId;

    @Value("${oauth2.client.kakao.url}")
    private String baseUrl;

    private final WebClient webClient;

    public KakaoOauth2UnlinkResponse unlink(long userId) {
        final Map<String, String> authorization = getAuthorization();
        final KakaoOauth2UnlinkRequest request = KakaoOauth2UnlinkRequest.builder()
                .targetIdType(TARGET_ID_TYPE)
                .targetId(userId)
                .build();
        final BodyInserters.FormInserter<String> body = BodyInserters
                .fromFormData("target_id_type", request.targetIdType())
                .with("target_id", Long.toString(request.targetId()));

        final KakaoOauth2UnlinkResponse response = webClient.mutate()
                .baseUrl(baseUrl).build()
                .post().uri(KakaoOauth2Api.UNLINK.getUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .headers(httpHeaders -> httpHeaders.setAll(authorization))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    log.error("KakaoOauth2ApiService.unlink api call error. response status: {}", clientResponse.statusCode());
                    return Mono.error(new ResponseStatusException(clientResponse.statusCode()));
                })
                .bodyToMono(KakaoOauth2UnlinkResponse.class)
                .block();

        return response;
    }

    private Map<String, String> getAuthorization() {
        return Map.of(
                "Authorization", "KakaoAK " + adminId
        );
    }
}
