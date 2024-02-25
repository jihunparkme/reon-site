package com.site.reon.aggregate.record.service.dto;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.site.reon.aggregate.record.domain.RoastingRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoastingRecordResponse {

    public static final RoastingRecordResponse EMPTY = new RoastingRecordResponse();

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("mm:ss");

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

    private float firstCrackPointTemp;
    private String firstCrackPointTime;
    private float secondCrackPointTemp;
    private String secondCrackPointTime;
    private float turningPointTemp; // 터닝 포인트 온도. [30.3]
    private String turningPointTime; // 터닝 포인트 시간. [2024-02-20 15:00:18 +0000]
    private float preheatTemp; // 예열 온도. 100.3
    private float disposeTemp; // 배출 온도. [95.3]
    private String disposeTime; //배출 시간. [2024-02-20 15:00:18 +0000]
    private int inputCapacity; // 용량(g). 40

    public static RoastingRecordResponse of(RoastingRecord roastingRecord) {

//        String crackPoint; // 크랙 포인트(1차, 2차). [30.3, 50.3]
//        String crackPointTime; // 크랙 시간(1차, 2차). [2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]

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
//                .firstCrackPointTemp()
//                .firstCrackPointTime()
//                .secondCrackPointTemp()
//                .secondCrackPointTime()
                .turningPointTemp(getSingleTemp(roastingRecord.getTurningPointTemp()))
                .turningPointTime(getSingleTime(roastingRecord.getTurningPointTime()))
                .preheatTemp(roastingRecord.getPreheatTemp())
                .disposeTemp(getSingleTemp(roastingRecord.getDisposeTemp()))
                .disposeTime(getSingleTime(roastingRecord.getDisposeTime()))
                .inputCapacity(roastingRecord.getInputCapacity())
                .createdDt(roastingRecord.getCreatedDt())
                .modifiedDt(roastingRecord.getModifiedDt())
                .build();
    }

    private static float getSingleTemp(String temp) {
        List<Float> temps = convertToFloatList(temp);
        if (temps.isEmpty()) {
            return 0F;
        }

        return temps.get(0);
    }

    private static String getSingleTime(String time) {
        List<String> times = convertToMMSSTimeList(time);
        if (times.isEmpty()) {
            return "";
        }

        return times.get(0);
    }

    /**
     * convert string to float list
     * [30.3] -> 30.3
     */
    static List<Float> convertToFloatList(String input) {
        if (StringUtils.isBlank(input) || "[]".equals(input)) {
            return Collections.emptyList();
        }

        input = input.substring(1, input.length() - 1);

        List<Float> result = new ArrayList<>();
        String[] items = input.split(",");
        for (String item : items) {
            result.add(Float.parseFloat(item.trim()));
        }

        return result;
    }

    /**
     * convert time string to MM:SS string list
     * [2024-02-20 15:00:18 +0000] -> 00:18
     */
    static List<String> convertToMMSSTimeList(String input) {
        if (StringUtils.isBlank(input) || "[]".equals(input)) {
            return Collections.emptyList();
        }

        input = input.substring(1, input.length() - 1);

        List<String> result = new ArrayList<>();
        String[] items = input.split(",");
        for (String item : items) {
            result.add(getHHSSTime(item.trim()));
        }

        return result;
    }

    private static String getHHSSTime(String item) {
        ZonedDateTime dateTime = ZonedDateTime.parse(item, DATE_TIME_FORMATTER);
        return dateTime.format(TIME_FORMATTER);
    }
}