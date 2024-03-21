package com.site.reon.aggregate.catalog.command.domain.product;

import com.site.reon.aggregate.common.jpa.MoneyConverter;
import com.site.reon.aggregate.common.model.Money;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"))
    private Set<Long> categoryIds;

    @Column(length = 100, nullable = false)
    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    @Column(length = 1000)
    private String detail;

    @Embedded
    private ProductNo productNo;

    @Embedded
    private SerialNo serialNo;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean activated;
}
