package com.site.reon.global.common.handler;

import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.exception.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;
import java.util.List;

import static com.site.reon.global.common.constant.Result.FAIL;

@Slf4j
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                return BasicResponse.clientError(allErrors.get(0).getDefaultMessage());
            }
            return BasicResponse.clientError(FAIL.message());
        }
        return BasicResponse.clientError(ex.getMessage());
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            BindException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            BadCredentialsException.class,
            MissingServletRequestParameterException.class,
            AccessDeniedException.class,
            DataAccessPermissionException.class,
    })
    public ResponseEntity handleBadRequest(Exception ex) {
        return BasicResponse.clientError(ex.getMessage());
    }

    @ExceptionHandler({
            DuplicateMemberException.class
    })
    public ResponseEntity handleCustomBadRequestException(Exception ex) {
        return BasicResponse.clientError(ex.getMessage());
    }

    @ExceptionHandler({
            NotFoundMemberException.class,
            NotFoundProductException.class,
            NotFoundRoastingRecordException.class,
            UsernameNotFoundException.class,
            EntityNotFoundException.class
    })
    public ResponseEntity handleCustomNotFoundException(Exception ex) {
        return BasicResponse.notFound(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("Exception. ", ex);
        return BasicResponse.internalServerError("An error occurred while processing the request. Please try again.");
    }

    @ExceptionHandler(BeanCreationNotAllowedException.class)
    public ResponseEntity handleBeanCreationNotAllowedException(Exception ex) {
        log.error("BeanCreationNotAllowedException. ", ex);
        return new ResponseEntity("BeanCreationNotAllowedException", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNoHandlerFoundException(Exception ex) {
        log.error("NoHandlerFoundException. ", ex);
        return BasicResponse.notFound("NoHandlerFoundException");
    }
}
