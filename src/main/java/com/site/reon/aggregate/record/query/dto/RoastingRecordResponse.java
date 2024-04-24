package com.site.reon.aggregate.record.query.dto;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.util.RecordUtils;
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

    private CreakInfo creakInfo;
    private float turningPointTemp;
    private String turningPointTime;
    private float coolingPointTemp;
    private String coolingPointTime;
    private float preheatTemp;
    private float disposeTemp;
    private String disposeTime;
    private int inputCapacity;
    private float dtr; // Development Time Ratio
    private int totalRoastingSecondsTime;
    private boolean pilot;

    public static RoastingRecordResponse of(final RoastingRecord roastingRecord) {
        final CreakInfo creakInfo = RecordUtils.generateCrackInfo(
                roastingRecord.getProfile().getCrackPoint().getCrackPoint(), roastingRecord.getProfile().getCrackPoint().getCrackPointTime());
        final int totalRoastingSecondsTime = RecordUtils.calculateRoastingLogsInSeconds(
                roastingRecord.getProfile().getTemperature().getTemp1());

        return RoastingRecordResponse.builder()
                .id(roastingRecord.getId())
                .title(roastingRecord.getRoastingInfo().getTitle())
                .roasterSn(roastingRecord.getRoastingInfo().getRoasterSn())
                .memberId(roastingRecord.getRoastingInfo().getMemberId())
                .preheatTemp(roastingRecord.getInputInfo().getPreheatTemp())
                .inputCapacity(roastingRecord.getInputInfo().getInputCapacity())
                .fan(roastingRecord.getProfile().getFan())
                .heater(roastingRecord.getProfile().getHeater())
                .ror(roastingRecord.getProfile().getRor())
                .temp1(roastingRecord.getProfile().getTemperature().getTemp1())
                .temp2(roastingRecord.getProfile().getTemperature().getTemp2())
                .temp3(roastingRecord.getProfile().getTemperature().getTemp3())
                .temp4(roastingRecord.getProfile().getTemperature().getTemp4())
                .creakInfo(creakInfo)
                .turningPointTemp(RecordUtils.getSingleTemp(roastingRecord.getProfile().getTurningPoint().getTurningPointTemp()))
                .turningPointTime(RecordUtils.getSingleTime(roastingRecord.getProfile().getTurningPoint().getTurningPointTime()))
                .coolingPointTemp(RecordUtils.getSingleTemp(roastingRecord.getProfile().getCoolingPoint().getCoolingPointTemp()))
                .coolingPointTime(RecordUtils.getSingleTime(roastingRecord.getProfile().getCoolingPoint().getCoolingPointTime()))
                .disposeTemp(RecordUtils.getSingleTemp(roastingRecord.getProfile().getDispose().getDisposeTemp()))
                .disposeTime(RecordUtils.getSingleTime(roastingRecord.getProfile().getDispose().getDisposeTime()))
                .createdDt(roastingRecord.getCreatedDt())
                .modifiedDt(roastingRecord.getModifiedDt())
                .dtr(RecordUtils.calculateDevelopmentTimeRatio(totalRoastingSecondsTime, creakInfo.getFirstCrackPointTime()))
                .totalRoastingSecondsTime(totalRoastingSecondsTime)
                .pilot(roastingRecord.isPilot())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreakInfo {
        public final static CreakInfo EMPTY = new CreakInfo();

        private float firstCrackPointTemp;
        private String firstCrackPointTime;
        private float secondCrackPointTemp;
        private String secondCrackPointTime;
    }
}