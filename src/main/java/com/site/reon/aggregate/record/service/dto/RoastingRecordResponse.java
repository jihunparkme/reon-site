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

    private String crackPoint; // 크랙 포인트(1차, 2차). [30.3, 50.3]
    private String crackPointTime; // 크랙 시간(1차, 2차). [2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]
    private String turningPointTemp; // 터닝 포인트 온도. [30.3]
    private String turningPointTime; // 터닝 포인트 시간. [2024-02-20 15:00:18 +0000]
    private float preheatTemp; // 예열 온도. 100.3
    private String disposeTemp; // 배출 온도. [95.3]
    private String disposeTime; //배출 시간. [2024-02-20 15:00:18 +0000]
    private int inputCapacity; // 용량(g). 40

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
                .crackPoint(roastingRecord.getCrackPoint())
                .crackPointTime(roastingRecord.getCrackPointTime())
                .turningPointTemp(roastingRecord.getTurningPointTemp())
                .turningPointTime(roastingRecord.getTurningPointTime())
                .preheatTemp(roastingRecord.getPreheatTemp())
                .disposeTemp(roastingRecord.getDisposeTemp())
                .disposeTime(roastingRecord.getDisposeTime())
                .inputCapacity(roastingRecord.getInputCapacity())
                .createdDt(roastingRecord.getCreatedDt())
                .modifiedDt(roastingRecord.getModifiedDt())
                .build();
    }
}