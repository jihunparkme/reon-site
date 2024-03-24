package com.site.reon.aggregate.catalog.command.domain.dto;

import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.ProductInfo;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductRequest {
    public static final SaveProductRequest EMPTY = new SaveProductRequest();
    
    @NotBlank(message = "category is required.")
    private Long categoryId;

    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "productNo is required.")
    private String productNo;

    @NotBlank(message = "color is required.")
    private Color color;

    @NotBlank(message = "ratedVoltage is required.")
    private RatedVoltage ratedVoltage;

    @NotBlank(message = "size is required.")
    private int size;

    public Product toProduct(final SerialNo serialNo) {
        final ProductInfo productInfo = ProductInfo.builder()
                .name(this.name)
                .detail(StringUtils.EMPTY)
                .productNo(ProductNo.of(this.productNo))
                .serialNo(serialNo)
                .manufacturedDt(LocalDateTime.now())
                .color(color)
                .ratedVoltage(ratedVoltage)
                .build();

        return Product.builder()
                .categoryIds(Set.of(this.categoryId))
                .productInfo(productInfo)
                .build();
    }
}