package com.site.reon.aggregate.record.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String title = StringUtils.EMPTY;
    private String date = StringUtils.EMPTY;
    private String roasterSn = StringUtils.EMPTY;
}
