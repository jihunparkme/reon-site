package com.site.reon.aggregate.record.query.dto.api;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.util.RecordUtils;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiRoastingRecordResponse(
        long id,
        String title,
        String fan,
        String fan2,
        String heater,
        String temp1,
        String temp2,
        String temp3,
        String temp4,
        String ror,
        String roasterSn,
        long memberId,
        String memo,
        String crackPoint,
        String crackPointTime,
        String turningPointTemp,
        String turningPointTime,
        String coolingPointTemp,
        String coolingPointTime,
        float preheatTemp,
        String disposeTemp,
        String disposeTime,
        int inputCapacity,
        float dtr,
        String createdDate,
        String createdTime
) {
    @Builder
    public ApiRoastingRecordResponse {
    }

    public static ApiRoastingRecordResponse of(final RoastingRecord record) {
        LocalDateTime dateTime = LocalDateTime.parse(record.getCreatedDt().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final RoastingRecordResponse.CreakInfo creakInfo = RecordUtils.generateCrackInfo(
                record.getProfile().getCrackPoint().getCrackPoint(), record.getProfile().getCrackPoint().getCrackPointTime());
        final int totalRoastingSecondsTime = RecordUtils.calculateRoastingLogsInSeconds(
                record.getProfile().getTemperature().getTemp1());
        final int coolingPointSecondsTime = RecordUtils.getPointTimeToSeconds(
                record.getProfile().getCoolingPoint().getCoolingPointTime(),
                totalRoastingSecondsTime);

        return ApiRoastingRecordResponse.builder()
                .id(record.getId())
                .title(record.getRoastingInfo().getTitle())
                .roasterSn(record.getRoastingInfo().getRoasterSn())
                .memberId(record.getRoastingInfo().getMemberId())
                .memo(record.getRoastingInfo().getMemo())
                .preheatTemp(record.getInputInfo().getPreheatTemp())
                .inputCapacity(record.getInputInfo().getInputCapacity())
                .fan(record.getProfile().getFan())
                .fan2(record.getProfile().getFan2())
                .heater(record.getProfile().getHeater())
                .ror(record.getProfile().getRor())
                .temp1(record.getProfile().getTemperature().getTemp1())
                .temp2(record.getProfile().getTemperature().getTemp2())
                .temp3(record.getProfile().getTemperature().getTemp3())
                .temp4(record.getProfile().getTemperature().getTemp4())
                .crackPoint(record.getProfile().getCrackPoint().getCrackPoint())
                .crackPointTime(record.getProfile().getCrackPoint().getCrackPointTime())
                .turningPointTemp(record.getProfile().getTurningPoint().getTurningPointTemp())
                .turningPointTime(record.getProfile().getTurningPoint().getTurningPointTime())
                .coolingPointTemp(record.getProfile().getCoolingPoint().getCoolingPointTemp())
                .coolingPointTime(record.getProfile().getCoolingPoint().getCoolingPointTime())
                .disposeTemp(record.getProfile().getDispose().getDisposeTemp())
                .disposeTime(record.getProfile().getDispose().getDisposeTime())
                .dtr(RecordUtils.calculateDevelopmentTimeRatio(coolingPointSecondsTime, creakInfo.getFirstCrackPointTime()))
                .createdDate(dateTime.toLocalDate().toString())
                .createdTime(dateTime.toLocalTime().withNano(0).toString())
                .build();
    }
}
