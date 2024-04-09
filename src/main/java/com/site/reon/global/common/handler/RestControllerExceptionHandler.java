package com.site.reon.global.common.handler;

import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.exception.NotFoundMemberException;
import com.site.reon.global.security.exception.NotFoundProductException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            BindException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity handleBadRequest(IllegalArgumentException ex) {
        return BasicResponse.clientError(ex.getMessage());
    }

    @ExceptionHandler({
            DuplicateMemberException.class
    })
    public ResponseEntity handleCustomBadRequestException(IllegalArgumentException ex) {
        return BasicResponse.clientError(ex.getMessage());
    }

    @ExceptionHandler({
            NotFoundMemberException.class,
            NotFoundProductException.class
    })
    public ResponseEntity handleCustomNotFoundException(IllegalArgumentException ex) {
        return BasicResponse.clientError(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("Exception. ", ex);
        return BasicResponse.internalServerError("Request processing failed. Please try again.");
    }

    @ExceptionHandler(BeanCreationNotAllowedException.class)
    public ResponseEntity handleBeanCreationNotAllowedException(Exception ex) {
        log.error("BeanCreationNotAllowedException. ", ex);
        return new ResponseEntity("BeanCreationNotAllowedException", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNoHandlerFoundException(Exception ex) {
        log.error("NoHandlerFoundException. ", ex);
        return new ResponseEntity("NoHandlerFoundException", HttpStatus.NOT_FOUND);
    }
}
