package com.site.reon.aggregate.record.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRecordSearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String title = StringUtils.EMPTY;
    private String serialNo = StringUtils.EMPTY;
    private String email = StringUtils.EMPTY;
    private String startDate = StringUtils.EMPTY;
    private String endDate = StringUtils.EMPTY;
}
