package com.site.reon.aggregate.record.query.dto.api;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiRoastingRecordResponse(
        long id,
        String title,
        String fan,
        String heater,
        String temp1,
        String temp2,
        String temp3,
        String temp4,
        String ror,
        String roasterSn,
        long memberId,
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
        String createdDate,
        String createdTime
) {
    @Builder
    public ApiRoastingRecordResponse {
    }

    public static ApiRoastingRecordResponse of(final RoastingRecord record) {
        LocalDateTime dateTime = LocalDateTime.parse(record.getCreatedDt().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ApiRoastingRecordResponse.builder()
                .id(record.getId())
                .title(record.getTitle())
                .fan(record.getFan())
                .heater(record.getHeater())
                .temp1(record.getTemp1())
                .temp2(record.getTemp2())
                .temp3(record.getTemp3())
                .temp4(record.getTemp4())
                .ror(record.getRor())
                .roasterSn(record.getRoasterSn())
                .memberId(record.getMemberId())
                .crackPoint(record.getCrackPoint())
                .crackPointTime(record.getCrackPointTime())
                .turningPointTemp(record.getTurningPointTemp())
                .turningPointTime(record.getTurningPointTime())
                .coolingPointTemp(record.getCoolingPoint().getCoolingPointTemp())
                .coolingPointTime(record.getCoolingPoint().getCoolingPointTime())
                .preheatTemp(record.getPreheatTemp())
                .disposeTemp(record.getDispose().getDisposeTemp())
                .disposeTime(record.getDispose().getDisposeTime())
                .inputCapacity(record.getInputCapacity())
                .createdDate(dateTime.toLocalDate().toString())
                .createdTime(dateTime.toLocalTime().withNano(0).toString())
                .build();
    }
}
