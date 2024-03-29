package com.site.reon.global.common.dto;

import com.site.reon.global.common.constant.Result;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Getter
@SuperBuilder
public class BasicResponse<T> {

    private int status;
    private HttpStatusCode httpStatusCode;
    private boolean success;

    private String message;

    private int count;
    private T data;

    public static ResponseEntity internalServerError(final String message) {
        BasicResponse basicResponse = BasicResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .message(message)
                .build();
        return new ResponseEntity<>(basicResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity clientError(final String message) {
        BasicResponse basicResponse = BasicResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .success(false)
                .message(message)
                .build();
        return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity ok(final boolean data) {
        BasicResponse<Object> basicResponse = BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .success(true)
                .data(data)
                .build();
        return ResponseEntity.ok(basicResponse);
    }

    public static <T> ResponseEntity ok(final T data) {
        BasicResponse<Object> basicResponse = BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .success(true)
                .count(1)
                .data(data)
                .build();
        return ResponseEntity.ok(basicResponse);
    }

    public static <T> ResponseEntity ok(final List<T> data) {
        BasicResponse<Object> basicResponse = BasicResponse.builder()
                .status(HttpStatus.OK.value())
                .httpStatusCode(HttpStatus.OK)
                .success(true)
                .count(data.size())
                .data(data)
                .build();
        return ResponseEntity.ok(basicResponse);
    }

    public static <T> ResponseEntity created(final T data) {
        BasicResponse<Object> basicResponse = BasicResponse.builder()
                .status(HttpStatus.CREATED.value())
                .httpStatusCode(HttpStatus.CREATED)
                .success(true)
                .count(1)
                .data(data)
                .build();
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity created(final List<T> data) {
        BasicResponse<Object> basicResponse = BasicResponse.builder()
                .status(HttpStatus.CREATED.value())
                .httpStatusCode(HttpStatus.CREATED)
                .success(true)
                .count(data.size())
                .data(data)
                .build();
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }
}