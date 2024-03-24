package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {
    @Override
    public List<String> saveProduct(final SaveProductRequest request) {

        // TODO: 시리얼넘버 생성하고
        // TODO: Product 저장
        return Collections.EMPTY_LIST;
    }
}
