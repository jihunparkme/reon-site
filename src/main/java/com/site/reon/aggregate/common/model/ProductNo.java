package com.site.reon.aggregate.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductNo {
    @Column(name = "product_no", length = 100, nullable = false)
    private String no;

    public static ProductNo of(final String productNo) {
        return new ProductNo(productNo);
    }
}
