package com.site.reon.aggregate.catalog.query.service;

import com.site.reon.aggregate.catalog.command.domain.product.Product;
import org.springframework.data.domain.Page;

public interface FindProductService {
    Page<Product> findAllOrderByIdDescPaging(int page, int size);
}
