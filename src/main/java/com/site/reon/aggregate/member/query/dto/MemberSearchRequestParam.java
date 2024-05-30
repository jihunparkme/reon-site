package com.site.reon.aggregate.member.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchRequestParam {
    private int page = 0;
    private int size = 10;

    private String name = StringUtils.EMPTY;
    private String email = StringUtils.EMPTY;
    private String serialNo = StringUtils.EMPTY;
}