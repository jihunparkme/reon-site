package com.site.reon.domain.record.dto;

import com.site.reon.domain.record.entity.RoastingRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoastingRecordResponse {

    public static final RoastingRecordResponse EMPTY = new RoastingRecordResponse();

    private Long id;
    private String title;
    private String record;
    private String roasterSn;
    private long userId;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;

    public static RoastingRecordResponse of(RoastingRecord roastingRecord) {
        return RoastingRecordResponse.builder()
                .id(roastingRecord.getId())
                .title(roastingRecord.getTitle())
                .record(roastingRecord.getRecord())
                .roasterSn(roastingRecord.getRoasterSn())
                .userId(roastingRecord.getUserId())
                .createdDt(roastingRecord.getCreatedDt())
                .modifiedDt(roastingRecord.getModifiedDt())
                .build();
    }
}
