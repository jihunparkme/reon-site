package com.site.reon.aggregate.catalog.query.service;

import com.site.reon.aggregate.catalog.query.dto.CategoryResponse;
import com.site.reon.aggregate.catalog.query.dto.ProductResponse;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductFindService {
    Page<Product> findAllOrderByIdDescPaging(int page, int size);

    ProductResponse findProductBy(Long id);

    List<CategoryResponse> findCategories();
}
