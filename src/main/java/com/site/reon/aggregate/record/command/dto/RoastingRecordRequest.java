package com.site.reon.aggregate.record.command.dto;

import com.site.reon.aggregate.record.command.domain.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoastingRecordRequest {
    @NotNull(message = "title is required.")
    private String title;

    @NotNull(message = "fan is required.")
    private String fan;

    private String fan2;

    @NotNull(message = "heater is required.")
    private String heater;

    @NotNull(message = "temp1 is required.")
    private String temp1;

    @NotNull(message = "temp2 is required.")
    private String temp2;

    @NotNull(message = "temp3 is required.")
    private String temp3;

    @NotNull(message = "temp4 is required.")
    private String temp4;

    @NotNull(message = "ror is required.")
    private String ror;

    @NotNull(message = "roasterSn is required.")
    private String roasterSn;

    @NotNull(message = "memberId is required.")
    private Long memberId;

    private String memo;

    @NotNull(message = "crackPoint is required.")
    private String crackPoint; // 크랙 포인트(1차, 2차). [30.3, 50.3]

    @NotNull(message = "crackPointTime is required.")
    private String crackPointTime; // 크랙 시간(1차, 2차). [2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]

    @NotNull(message = "turningPointTemp is required.")
    private String turningPointTemp; // 터닝 포인트 온도. [30.3]

    @NotNull(message = "turningPointTime is required.")
    private String turningPointTime; // 터닝 포인트 시간. [2024-02-20 15:00:18 +0000]

    @NotNull(message = "coolingPointTemp is required.")
    private String coolingPointTemp; // 쿨링 포인트 온도. [30.3]

    @NotNull(message = "coolingPointTime is required.")
    private String coolingPointTime; // 쿨링 포인트 시간. [2024-02-20 15:00:18 +0000]

    @NotNull(message = "preheatTemp is required.")
    private Float preheatTemp; // 예열 온도. 100.3

    @NotNull(message = "disposeTemp is required.")
    private String disposeTemp; // 배출 온도. [95.3]

    @NotNull(message = "disposeTime is required.")
    private String disposeTime; //배출 시간. [2024-02-20 15:00:18 +0000]

    @NotNull(message = "inputCapacity is required.")
    private Integer inputCapacity; // 용량(g). 40

    public RoastingRecordRequest(
            final String title,
            final String fan,
            final String heater,
            final String temp1,
            final String temp2,
            final String temp3,
            final String temp4,
            final String ror,
            final String roasterSn,
            final long memberId) {
        this.title = title;
        this.fan = fan;
        this.heater = heater;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
        this.temp4 = temp4;
        this.ror = ror;
        this.roasterSn = roasterSn;
        this.memberId = memberId;
    }

    public RoastingRecord toEntity(final long memberId) {
        return RoastingRecord.builder()
                .roastingInfo(RoastingInfo.builder()
                        .title(this.title)
                        .roasterSn(this.roasterSn)
                        .memberId(memberId)
                        .memo(this.memo)
                        .build())
                .inputInfo(InputInfo.builder()
                        .preheatTemp(this.preheatTemp)
                        .inputCapacity(this.inputCapacity)
                        .build())
                .profile(Profile.builder()
                        .fan(this.fan)
                        .fan2(this.fan2)
                        .heater(this.heater)
                        .ror(this.ror)
                        .temperature(Temperature.builder()
                                .temp1(this.temp1)
                                .temp2(this.temp2)
                                .temp3(this.temp3)
                                .temp4(this.temp4)
                                .build())
                        .crackPoint(CrackPoint.builder()
                                .crackPoint(this.crackPoint)
                                .crackPointTime(this.crackPointTime)
                                .build())
                        .turningPoint(TurningPoint.builder()
                                .turningPointTemp(this.turningPointTemp)
                                .turningPointTime(this.turningPointTime)
                                .build())
                        .coolingPoint(CoolingPoint.builder()
                                .coolingPointTemp(this.coolingPointTemp)
                                .coolingPointTime(this.coolingPointTime)
                                .build())
                        .dispose(Dispose.builder()
                                .disposeTemp(this.disposeTemp)
                                .disposeTime(this.disposeTime)
                                .build())
                        .build())
                .build();
    }
}