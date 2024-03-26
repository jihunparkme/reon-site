package com.site.reon.aggregate.catalog.controller;

import com.site.reon.aggregate.catalog.command.domain.dto.CategoryResponse;
import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import com.site.reon.aggregate.catalog.query.service.ProductFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductAdminPageController {

    private final ProductFindService productService;

    @GetMapping
    public String findProducts(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            Model model) {
        // TODO: 카테고리, 제품명, 상품코드, 시리얼넘버, 생산날짜, 색상, 전압 으로 검색
        final var productListPage = productService.findAllOrderByIdDescPaging(page, size);

        model.addAttribute("productListPage", productListPage);
        model.addAttribute("page", page);
        return "admin/products/product-list";
    }

    @GetMapping("{id}")
    public String finsProduct(@PathVariable(name = "id") final Long id,
                       Model model) {
        final var product = productService.findProductBy(id);

        model.addAttribute("product", product);
        return "admin/products/product-view";
    }

    @GetMapping("serial-no")
    public String createSerialNo(Model model) {
        final List<CategoryResponse> categories = productService.findCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("colors", Color.values());
        model.addAttribute("ratedVoltages", RatedVoltage.values());
        model.addAttribute("product", SaveProductRequest.EMPTY);
        return "admin/products/serial-no";
    }
}
