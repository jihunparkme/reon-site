package com.site.reon.aggregate.catalog.command.domain.product;

import com.site.reon.aggregate.common.jpa.MoneyConverter;
import com.site.reon.aggregate.common.model.Money;
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

    @Column(length = 100, nullable = false)
    private String productCode;

    @Column(length = 100, nullable = false)
    private String productSn;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean activated;
}
