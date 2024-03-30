package com.site.reon.aggregate.catalog.controller;

import com.site.reon.aggregate.catalog.command.domain.dto.RegisterSerialNoRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.RegisterSerialNoResponse;
import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.service.ProductCommandService;
import com.site.reon.aggregate.common.model.SerialNo;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.common.util.BindingResultUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductAdminController {
    private final ProductCommandService productCommandService;

    @PostMapping
    public ResponseEntity saveProduct(@Valid @RequestBody final SaveProductRequest request,
                                         final BindingResult bindingResult) {
        final ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            final List<SerialNo> serialNos = productCommandService.saveProducts(request);
            return BasicResponse.created(serialNos);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("ProductAdminController.saveProduct Exception: ", e);
            return BasicResponse.internalServerError("Failed to create product. Please try again.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable(name = "id") final Long id) {
        try {
            productCommandService.delete(id);
            return BasicResponse.ok(true);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("ProductAdminController.deleteProduct Exception: ", e);
            return BasicResponse.internalServerError("Failed to delete product. Please try again.");
        }
    }

    @PutMapping("/register/serial-no")
    public ResponseEntity registerSerialNo(@RequestBody final RegisterSerialNoRequest request) {
        try {
            final RegisterSerialNoResponse registerSerialNoResponse = productCommandService.activateSerialNo(request);
            return BasicResponse.ok(registerSerialNoResponse);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("ProductAdminController.registerSerialNo Exception: ", e);
            return BasicResponse.internalServerError("Failed to activate S/N. Please try again.");
        }
    }
}
