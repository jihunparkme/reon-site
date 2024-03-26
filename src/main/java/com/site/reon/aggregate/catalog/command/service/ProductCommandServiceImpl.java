package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.ProductRepository;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public List<SerialNo> saveProducts(final SaveProductRequest request) {
        List<SerialNo> serialNos = createSerialNo(request);
        final List<Product> products = serialNos.stream()
                .map(serialNo -> request.toProduct(serialNo))
                .collect(Collectors.toList());

        products.stream()
                .forEach(product -> productRepository.save(product));

        return serialNos;
    }

    private List<SerialNo> createSerialNo(final SaveProductRequest request) {
        final int size = request.getSize();
        final ProductNo productNo = ProductNo.of(request.getProductNo());
        final LocalDate today = LocalDate.now();
        final int quantity = getQuantityOfProductsProducedToday(productNo, today);

        final List<SerialNo> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(SerialNo.builder()
                    .productNo(productNo)
                    .createdNo(quantity + (i + 1))
                    .date(today)
                    .build());
        }

        return result;
    }

    private int getQuantityOfProductsProducedToday(final ProductNo productNo, final LocalDate today) {
        final LocalDateTime startOfDay = today.atStartOfDay();
        final LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
        return productRepository.quantityOfProductsProducedToday(productNo, startOfDay, endOfDay);
    }
}
