package com.site.reon.aggregate.catalog.command.domain.product.repository;

import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.query.dto.ProductSearchRequestParam;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {
    Page<Product> findAllByFilter(ProductSearchRequestParam param);
}
