package com.site.reon.aggregate.catalog.command.domain.product;

import com.site.reon.aggregate.catalog.command.domain.dto.UpdateProductRequest;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import com.site.reon.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"))
    private Set<Long> categoryIds;

    @Embedded
    private ProductInfo productInfo;

    public void update(final UpdateProductRequest request) {
        this.categoryIds = request.getCategoryIds();
        final ProductInfo productInfo = ProductInfo.builder()
                .name(request.getName())
                .detail(request.getDetail())
                .productNo(ProductNo.of(request.getProductNo()))
                .serialNo(SerialNo.of(request.getSerialNo(), this.productInfo.getSerialNo().isActivated()))
                .manufacturedDt(this.productInfo.getManufacturedDt())
                .color(request.getColor())
                .ratedVoltage(request.getRatedVoltage())
                .build();
        this.productInfo = productInfo;
    }
}