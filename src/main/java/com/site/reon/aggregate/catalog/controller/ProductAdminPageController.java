package com.site.reon.aggregate.catalog.controller;

import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.UpdateProductRequest;
import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import com.site.reon.aggregate.catalog.command.service.ProductCommandService;
import com.site.reon.aggregate.catalog.query.service.ProductFindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductAdminPageController {

    private final ProductFindService productFindService;
    private final ProductCommandService productCommandService;

    @GetMapping
    public String findProducts(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            Model model) {
        // TODO: 카테고리, 제품명, 상품코드, 시리얼넘버, 생산날짜, 색상, 전압 으로 검색
        final var productListPage = productFindService.findAllOrderByIdDescPaging(page, size);

        model.addAttribute("productListPage", productListPage);
        model.addAttribute("page", page);
        return "admin/products/product-list";
    }

    @GetMapping("/{id}")
    public String findProduct(@PathVariable(name = "id") final Long id, Model model) {
        final var product = productFindService.findProductBy(id);

        model.addAttribute("product", product);
        addSelectAttribute(model);
        return "admin/products/product-view";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable(name = "id") final Long id,
                              @Valid @ModelAttribute("product") final UpdateProductRequest request,
                              final BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            addSelectAttribute(model);
            return "admin/products/product-view";
        }

        try {
            productCommandService.update(id, request);
        } catch (Exception e) {
            addSelectAttribute(model);
            bindingResult.reject("global.error", e.getMessage());
            return "admin/products/product-view";
        }

        redirectAttributes.addAttribute("status", true);
        return "redirect:" + "/admin/products/" + id;
    }

    @GetMapping("/create/serial-no")
    public String createSerialNo(Model model) {
        addSelectAttribute(model);
        model.addAttribute("product", SaveProductRequest.EMPTY);
        return "admin/products/serial-no";
    }

    private void addSelectAttribute(final Model model) {
        model.addAttribute("categories", productFindService.findCategories());
        model.addAttribute("colors", Color.values());
        model.addAttribute("ratedVoltages", RatedVoltage.values());
    }
}
