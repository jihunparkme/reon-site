package com.site.reon.aggregate.record.service.dto;

import com.site.reon.aggregate.record.domain.RoastingRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoastingRecordRequest {
    private String title;
    private String fan;
    private String heater;
    private String temp1;
    private String temp2;
    private String temp3;
    private String temp4;
    private String ror;
    private String roasterSn;

    public RoastingRecord toEntity(long memberId) {
        return RoastingRecord.builder()
                .title(this.title)
                .fan(this.fan)
                .heater(this.heater)
                .temp1(this.temp1)
                .temp2(this.temp2)
                .temp3(this.temp3)
                .temp4(this.temp4)
                .ror(this.ror)
                .roasterSn(this.roasterSn)
                .memberId(memberId)
                .build();
    }
}