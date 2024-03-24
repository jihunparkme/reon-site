package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;

import java.util.List;

public interface ProductCommandService {
    List<String> saveProduct(SaveProductRequest request);
}
