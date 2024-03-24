package com.site.reon.aggregate.catalog.command.domain.product;

import com.site.reon.aggregate.common.model.ProductNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT count(product) " +
            "FROM Product product " +
            "WHERE product.productInfo.productNo = :productNo " +
            "AND product.productInfo.manufacturedDt BETWEEN :startOfDay AND :endOfDay")
    int quantityOfProductsProducedToday(@Param("productNo") final ProductNo productNo,
                                        @Param("startOfDay") LocalDateTime startOfDay,
                                        @Param("endOfDay") LocalDateTime endOfDay);
}
