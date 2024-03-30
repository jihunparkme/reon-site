package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.RegisterSerialNoRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.RegisterSerialNoResponse;
import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.UpdateProductRequest;
import com.site.reon.aggregate.common.model.SerialNo;

import java.util.List;

public interface ProductCommandService {
    List<SerialNo> saveProducts(SaveProductRequest request);

    void update(Long id, UpdateProductRequest request);

    void delete(Long id);

    RegisterSerialNoResponse activateSerialNo(RegisterSerialNoRequest request);
}
