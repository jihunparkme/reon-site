package com.site.reon.global.common.handler;

import com.site.reon.global.security.exception.DataAccessPermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ControllerExceptionHandler {
    @ExceptionHandler(DataAccessPermissionException.class)
    public String dataAccessPermissionException(Exception ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        return "error/alert";
    }
}
