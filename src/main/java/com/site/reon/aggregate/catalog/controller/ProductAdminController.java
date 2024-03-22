package com.site.reon.aggregate.catalog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductAdminController {

    @GetMapping
    public String list(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            Model model) {

        model.addAttribute("productListPage", null);
        model.addAttribute("page", page);
        return "admin/products/product-list";
    }

    @GetMapping("{id}")
    public String view(@PathVariable(name = "id") final Long id,
                       Model model) {

        model.addAttribute("record", null);
        return "admin/products/product-view";
    }
}
