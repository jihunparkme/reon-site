package com.site.reon.aggregate.member.infra.kakao.service;

import com.site.reon.aggregate.member.infra.kakao.dto.KakaoOauth2UnlinkResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class KakaoOauth2ApiServiceTest {

    @Mock
    private KakaoOauth2ApiService apiService;

    @Test
    void when_unlink_then_return_user_id() {
        final long targetId = 1234567899L;
        final String msg = null;
        final Long code = null;
        final var expected = new KakaoOauth2UnlinkResponse(targetId, msg, code);

        given(apiService.unlink(anyLong())).willReturn(expected);

        final var response = apiService.unlink(targetId);

        Assertions.assertEquals(targetId, response.id());
    }

    @Test
    void when_unlink_then_return_NotRegisteredUserException() {
        final long targetId = 1234567899L;
        final String msg = "NotRegisteredUserException";
        final Long code = -101L;
        final var expected = new KakaoOauth2UnlinkResponse(null, msg, code);

        given(apiService.unlink(anyLong())).willReturn(expected);

        final var response = apiService.unlink(targetId);

        Assertions.assertEquals(msg, response.msg());
        Assertions.assertEquals(code, response.code());
    }
}