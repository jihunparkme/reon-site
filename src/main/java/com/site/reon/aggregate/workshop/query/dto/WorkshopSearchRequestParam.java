package com.site.reon.aggregate.workshop.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopSearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String title = StringUtils.EMPTY;
    private String name = StringUtils.EMPTY;
    private String startDate = StringUtils.EMPTY;
    private String endDate = StringUtils.EMPTY;
}
