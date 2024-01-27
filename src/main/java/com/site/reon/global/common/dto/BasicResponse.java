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
    private T data;

    public static BasicResponse internalServerError(String message) {
        return BasicResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .build();
    }

    public static BasicResponse ok(boolean data) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> BasicResponse ok(T data) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .count(1)
                .data(data)
                .build();
    }

    public static <T> BasicResponse ok(List<T> data) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .count(data.size())
                .data(data)
                .build();
    }
}