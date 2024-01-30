package com.site.reon.global.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
@SuperBuilder
public class BasicResponse<T> {

    private int status;
    private HttpStatusCode httpStatusCode;
    private boolean success;

    private String message;

    private int count;
    private T data;

    public static BasicResponse internalServerError(final String message) {
        return BasicResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .message(message)
                .build();
    }

    public static BasicResponse clientError(final String message) {
        return BasicResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .success(false)
                .message(message)
                .build();
    }

    public static BasicResponse ok(final boolean data) {
        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .success(true)
                .data(data)
                .build();
    }

    public static <T> BasicResponse ok(final T data) {
        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .success(true)
                .count(1)
                .data(data)
                .build();
    }

    public static <T> BasicResponse ok(final List<T> data) {
        return BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .success(true)
                .count(data.size())
                .data(data)
                .build();
    }
}