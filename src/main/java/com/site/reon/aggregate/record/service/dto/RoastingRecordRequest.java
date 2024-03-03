package com.site.reon.aggregate.record.service.dto;

import com.site.reon.aggregate.record.domain.RoastingRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
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

    private String crackPoint; // 크랙 포인트(1차, 2차). [30.3, 50.3]
    private String crackPointTime; // 크랙 시간(1차, 2차). [2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]
    private String turningPointTemp; // 터닝 포인트 온도. [30.3]
    private String turningPointTime; // 터닝 포인트 시간. [2024-02-20 15:00:18 +0000]
    private float preheatTemp; // 예열 온도. 100.3
    private String disposeTemp; // 배출 온도. [95.3]
    private String disposeTime; //배출 시간. [2024-02-20 15:00:18 +0000]
    private int inputCapacity; // 용량(g). 40

    public RoastingRecordRequest(
            final String title,
            final String fan,
            final String heater,
            final String temp1,
            final String temp2,
            final String temp3,
            final String temp4,
            final String ror,
            final String roasterSn) {
        this.title = title;
        this.fan = fan;
        this.heater = heater;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
        this.temp4 = temp4;
        this.ror = ror;
        this.roasterSn = roasterSn;
    }

    public RoastingRecord toEntity(final long memberId) {
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
                .crackPoint(this.crackPoint)
                .crackPointTime(this.crackPointTime)
                .turningPointTemp(this.turningPointTemp)
                .turningPointTime(this.turningPointTime)
                .preheatTemp(this.preheatTemp)
                .disposeTemp(this.disposeTemp)
                .disposeTime(this.disposeTime)
                .inputCapacity(this.inputCapacity)
                .build();
    }
}