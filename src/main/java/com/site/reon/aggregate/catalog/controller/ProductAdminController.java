package com.site.reon.aggregate.catalog.controller;

import com.site.reon.aggregate.catalog.command.domain.dto.SerialNoRequest;
import com.site.reon.aggregate.catalog.command.service.ProductCommandService;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.common.util.BindingResultUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductAdminController {
    private final ProductCommandService productCommandService;

    @PostMapping("serial-no")
    public ResponseEntity createSerialNo(@Valid @RequestBody final SerialNoRequest request,
                                         final BindingResult bindingResult) {
        final ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            final List<String> serialNos = productCommandService.createSerialNo(request);
            return BasicResponse.created(serialNos);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("ProductAdminController.createSerialNo Exception: ", e);
            return BasicResponse.internalServerError("Failed to create serial no. Please try again.");
        }
    }
}
