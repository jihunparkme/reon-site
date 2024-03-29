package com.site.reon.aggregate.catalog.command.domain.dto;

import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private Long id;
    
    @NotEmpty(message = "category must not be empty.")
    private Set<Long> categoryIds;

    @Size(min = 1, max = 100, message = "The name length must be between 1 and 100 characters.")
    private String name;

    @Size(max = 1000, message = "The last name length must not exceed 1000 characters.")
    private String detail;

    @Size(min = 1, max = 100, message = "The name length must be between 1 and 100 characters.")
    private String productNo;

    @Size(min = 1, max = 100, message = "The name length must be between 1 and 100 characters.")
    private String serialNo;

    private String manufacturedDt;

    @NotNull(message = "color is required.")
    private Color color;

    @NotNull(message = "rated voltage is required.")
    private RatedVoltage ratedVoltage;
}
