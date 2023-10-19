package com.site.reon.domain.record.dto;

import com.site.reon.domain.record.entity.RoastingRecord;
import lombok.Getter;

@Getter
public class RoastingRecordRequest {
    private String title;
    private String request;
    private String prdSn;

    public RoastingRecord toEntity(long userId) {
        return RoastingRecord.builder()
                .title(title)
                .request(request)
                .prdSn(prdSn)
                .userId(userId)
                .build();
    }
}
