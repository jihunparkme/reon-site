package com.site.reon.global.common.util;

import com.site.reon.global.common.dto.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static com.site.reon.global.common.constant.Result.FAIL;

public class BindingResultUtil {
    public static ResponseEntity validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                return BasicResponse.clientError(allErrors.get(0).getDefaultMessage());
            }
            return BasicResponse.clientError(FAIL.message());
        }
        return null;
    }
}
