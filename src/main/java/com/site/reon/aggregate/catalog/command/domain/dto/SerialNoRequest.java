package com.site.reon.aggregate.catalog.command.domain.dto;

import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerialNoRequest {
    public static final SerialNoRequest EMPTY = new SerialNoRequest();
    
    @NotBlank(message = "category is required.")
    private String category;

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
}
