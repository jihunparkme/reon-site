package com.site.reon.aggregate.catalog.query.dto;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String modelName = StringUtils.EMPTY;
    private String productNo = StringUtils.EMPTY;
    private String serialNo = StringUtils.EMPTY;
    private Boolean activated = null;
    private String startDate = StringUtils.EMPTY;
    private String endDate = StringUtils.EMPTY;
}
