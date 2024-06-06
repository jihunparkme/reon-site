package com.site.reon.aggregate.catalog.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String category = StringUtils.EMPTY;
    private String modelName = StringUtils.EMPTY;
    private String productNo = StringUtils.EMPTY;
    private String serialNo = StringUtils.EMPTY;
    private boolean activated = true;
    private String startDate = StringUtils.EMPTY;
    private String endDate = StringUtils.EMPTY;
}
