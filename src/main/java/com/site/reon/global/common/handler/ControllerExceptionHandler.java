package com.site.reon.global.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Order(2)
@ControllerAdvice(annotations = Controller.class)
public class ControllerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public String dataAccessPermissionException(Exception ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        return "error/alert";
    }
}
