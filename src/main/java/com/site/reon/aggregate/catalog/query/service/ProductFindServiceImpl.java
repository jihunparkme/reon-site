package com.site.reon.aggregate.catalog.query.service;

import com.site.reon.aggregate.catalog.command.domain.category.Category;
import com.site.reon.aggregate.catalog.command.domain.category.CategoryRepository;
import com.site.reon.aggregate.catalog.command.domain.dto.CategoryResponse;
import com.site.reon.aggregate.catalog.command.domain.dto.ProductResponse;
import com.site.reon.aggregate.catalog.command.domain.dto.UpdateProductRequest;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.ProductRepository;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.global.security.exception.NotFoundMemberException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFindServiceImpl implements ProductFindService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Product> findAllOrderByIdDescPaging(final int page, final int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findAll(pageable);
    }

    @Override
    public ProductResponse findProductBy(final Long id) {
        final Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new EntityNotFoundException("Not Found Product");
        }

        return ProductResponse.of(productOpt.get());
    }

    @Override
    public List<CategoryResponse> findCategories() {
        final List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public void update(final Long id, final UpdateProductRequest request) {
        final Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new NotFoundMemberException("Not Found Product");
        }

        final Product product = productOpt.get();
        product.update(request);
        productRepository.save(product);
    }
}
