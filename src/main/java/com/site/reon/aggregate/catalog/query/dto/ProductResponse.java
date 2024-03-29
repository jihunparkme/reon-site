package com.site.reon.aggregate.catalog.query.dto;

import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.ProductInfo;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import lombok.Builder;

import java.util.Set;

public record ProductResponse(
        Long id,
        Set<Long> categoryIds,
        String name,
        String detail,
        String productNo,
        String serialNo,
        String manufacturedDt,
        Color color,
        RatedVoltage ratedVoltage
) {
    @Builder
    public ProductResponse {
    }

    public static ProductResponse of(final Product product) {
        final ProductInfo productInfo = product.getProductInfo();
        return ProductResponse.builder()
                .id(product.getId())
                .categoryIds(product.getCategoryIds())
                .name(productInfo.getName())
                .detail(productInfo.getDetail())
                .productNo(productInfo.getProductNo().getNo())
                .serialNo(productInfo.getSerialNo().getNo())
                .manufacturedDt(productInfo.getManufacturedDt().toLocalDate().toString())
                .color(productInfo.getColor())
                .ratedVoltage(productInfo.getRatedVoltage())
                .build();
    }
}
