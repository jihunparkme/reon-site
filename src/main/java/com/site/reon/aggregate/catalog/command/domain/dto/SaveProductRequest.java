package com.site.reon.aggregate.catalog.command.domain.dto;

import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.ProductInfo;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductRequest {
    public static final SaveProductRequest EMPTY = new SaveProductRequest();
    
    @NotNull(message = "category is required.")
    private Long categoryId;

    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "product no is required.")
    private String productNo;

    @NotNull(message = "color is required.")
    private Color color;

    @NotNull(message = "rated voltage is required.")
    private RatedVoltage ratedVoltage;

    @Min( value = 1, message = "size must be at least greater than 1.")
    private int size;

    @NotNull(message = "manufactured date is required.")
    LocalDate manufacturedDt;

    public Product toProduct(final SerialNo serialNo) {
        final ProductInfo productInfo = ProductInfo.builder()
                .name(this.name)
                .detail(StringUtils.EMPTY)
                .productNo(ProductNo.of(this.productNo))
                .serialNo(serialNo)
                .manufacturedDt(this.manufacturedDt.atStartOfDay())
                .color(color)
                .ratedVoltage(ratedVoltage)
                .build();

        return Product.builder()
                .categoryIds(Set.of(this.categoryId))
                .productInfo(productInfo)
                .build();
    }
}