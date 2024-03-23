package com.site.reon.aggregate.catalog.query.service;

import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindProductServiceImpl implements FindProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAllOrderByIdDescPaging(final int page, final int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findAll(pageable);
    }
}
