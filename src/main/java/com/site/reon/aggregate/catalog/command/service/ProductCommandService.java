package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.UpdateProductRequest;
import com.site.reon.aggregate.common.model.SerialNo;

import java.util.List;

public interface ProductCommandService {
    List<SerialNo> saveProducts(SaveProductRequest request);
    void update(final Long id, UpdateProductRequest request);
}
