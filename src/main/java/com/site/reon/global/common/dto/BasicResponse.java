package com.site.reon.global.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
@SuperBuilder
public class BasicResponse<T> {

    private Integer code;
    private HttpStatusCode httpStatusCode;

    private String message;

    private Integer count;
    private T result;

    public static BasicResponse internalServerError(String message) {
        return BasicResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .build();
    }

    public static BasicResponse ok(boolean result) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .result(result)
                .build();
    }

    public static <T> BasicResponse ok(T result) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .count(1)
                .result(result)
                .build();
    }

    public static <T> BasicResponse ok(List<T> result) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .count(result.size())
                .result(result)
                .build();
    }
}