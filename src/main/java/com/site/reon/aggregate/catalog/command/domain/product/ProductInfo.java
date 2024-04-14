package com.site.reon.aggregate.catalog.command.domain.product;

import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String detail;

    @Embedded
    private ProductNo productNo;

    @Embedded
    private SerialNo serialNo;

    private LocalDateTime manufacturedDt;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private RatedVoltage ratedVoltage;
}