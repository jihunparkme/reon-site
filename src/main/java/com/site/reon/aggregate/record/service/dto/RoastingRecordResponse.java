package com.site.reon.aggregate.record.service.dto;

import com.site.reon.aggregate.record.domain.RoastingRecord;
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
    private String fan;
    private String heater;
    private String temp1;
    private String temp2;
    private String temp3;
    private String temp4;
    private String ror;
    private String roasterSn;
    private long memberId;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;

    public static RoastingRecordResponse of(RoastingRecord roastingRecord) {
        return RoastingRecordResponse.builder()
                .id(roastingRecord.getId())
                .title(roastingRecord.getTitle())
                .fan(roastingRecord.getFan())
                .heater(roastingRecord.getHeater())
                .temp1(roastingRecord.getTemp1())
                .temp2(roastingRecord.getTemp2())
                .temp3(roastingRecord.getTemp3())
                .temp4(roastingRecord.getTemp4())
                .ror(roastingRecord.getRor())
                .roasterSn(roastingRecord.getRoasterSn())
                .memberId(roastingRecord.getMemberId())
                .createdDt(roastingRecord.getCreatedDt())
                .modifiedDt(roastingRecord.getModifiedDt())
                .build();
    }
}
