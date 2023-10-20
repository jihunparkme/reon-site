package com.site.reon.domain.record.dto;

import com.site.reon.domain.record.entity.RoastingRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoastingRecordRequest {
    private String title;
    private String record;
    private String roasterSn;

    public RoastingRecord toEntity(long userId) {
        return RoastingRecord.builder()
                .title(title)
                .record(record)
                .roasterSn(roasterSn)
                .userId(userId)
                .build();
    }
}
