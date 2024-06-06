package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.*;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.repository.ProductRepository;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import com.site.reon.global.security.exception.NotFoundProductException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    @Transactional
    public void update(final Long id, final UpdateProductRequest request) {
        final Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new NotFoundProductException();
        }

        final Product product = productOpt.get();
        product.update(request);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        final Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new NotFoundProductException();
        }

        final Product product = productOpt.get();
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public RegisterSerialNoResponse activateSerialNo(final RegisterSerialNoRequest request) {
        final List<String> serialNos = request.serialNos();
        if (serialNos.size() < 1 || StringUtils.isEmpty(serialNos.get(0))) {
            throw new IllegalArgumentException("Please enter the serial no.");
        }

        final List<SerialNoRegisterResult> serialNoRegisterResults = serialNos.stream()
                .map(serialNo -> {
                    final int result = productRepository.activateSerialNo(serialNo);
                    return SerialNoRegisterResult.builder()
                            .serialNo(serialNo)
                            .result(result == 1)
                            .build();
                })
                .collect(Collectors.toList());

        return RegisterSerialNoResponse.builder()
                .registerResult(serialNoRegisterResults)
                .build();
    }

    private List<SerialNo> createSerialNo(final SaveProductRequest request) {
        final int size = request.getSize();
        final ProductNo productNo = ProductNo.of(request.getProductNo());
        final LocalDate manufacturedDt = request.getManufacturedDt();
        final int quantity = getQuantityOfProductsProducedToday(productNo, manufacturedDt);

        final List<SerialNo> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(SerialNo.builder()
                    .productNo(productNo)
                    .createdNo(quantity + (i + 1))
                    .date(manufacturedDt)
                    .build());
        }

        return result;
    }

    private int getQuantityOfProductsProducedToday(final ProductNo productNo, final LocalDate manufacturedDt) {
        final LocalDateTime startOfDay = manufacturedDt.atStartOfDay();
        final LocalDateTime endOfDay = manufacturedDt.plusDays(1).atStartOfDay();
        return productRepository.quantityOfProductsProducedToday(productNo, startOfDay, endOfDay);
    }
}
