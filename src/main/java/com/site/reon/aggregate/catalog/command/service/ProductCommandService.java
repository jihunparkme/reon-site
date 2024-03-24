package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.SerialNoRequest;

import java.util.List;

public interface ProductCommandService {
    List<String> createSerialNo(SerialNoRequest request);
}
