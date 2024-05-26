package com.site.reon.aggregate.record.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordSearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String title = StringUtils.EMPTY;
    private String startDate = StringUtils.EMPTY;
    private String endDate = StringUtils.EMPTY;
}
