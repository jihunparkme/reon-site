package com.site.reon.aggregate.catalog.command.domain.product.repository;

import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.query.dto.ProductSearchRequestParam;
import com.site.reon.global.common.config.QueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDslConfig.class)
class ProductRepositoryCustomImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void when_findAllByFilter_then_return_all_product() throws Exception {
        final ProductSearchRequestParam param = ProductSearchRequestParam.builder()
                .page(0)
                .size(10)
                .modelName("")
                .productNo("")
                .serialNo("")
                .activated(true)
                .startDate("")
                .endDate("")
                .build();
        final Page<Product> result = productRepository.findAllByFilter(param);

        assertEquals(6, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByFilter_then_return_model_name_filtered_product() throws Exception {
        final ProductSearchRequestParam param = ProductSearchRequestParam.builder()
                .page(0)
                .size(10)
                .modelName("R500")
                .productNo("")
                .serialNo("")
                .activated(true)
                .startDate("")
                .endDate("")
                .build();
        final Page<Product> result = productRepository.findAllByFilter(param);

        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByFilter_then_return_product_no_filtered_product() throws Exception {
        final ProductSearchRequestParam param = ProductSearchRequestParam.builder()
                .page(0)
                .size(10)
                .modelName("")
                .productNo("r2n1bk")
                .serialNo("")
                .activated(null)
                .startDate("")
                .endDate("")
                .build();
        final Page<Product> result = productRepository.findAllByFilter(param);

        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByFilter_then_return_serial_no_filtered_product() throws Exception {
        final ProductSearchRequestParam param = ProductSearchRequestParam.builder()
                .page(0)
                .size(10)
                .modelName("")
                .productNo("")
                .serialNo("R2N0BK-0001-20240606")
                .activated(true)
                .startDate("")
                .endDate("")
                .build();
        final Page<Product> result = productRepository.findAllByFilter(param);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void when_findAllByFilter_then_return_manufacture_date_and_product_no_filtered_product() throws Exception {
        final ProductSearchRequestParam param = ProductSearchRequestParam.builder()
                .page(0)
                .size(10)
                .modelName("")
                .productNo("r2n1bk")
                .serialNo("")
                .activated(true)
                .startDate("2024-06-01")
                .endDate("2024-06-06")
                .build();
        final Page<Product> result = productRepository.findAllByFilter(param);

        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }
}