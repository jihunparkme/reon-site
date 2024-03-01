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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private CreakInfo creakInfo;
    private float turningPointTemp;
    private String turningPointTime;
    private float preheatTemp;
    private float disposeTemp;
    private String disposeTime;
    private int inputCapacity;

    public static RoastingRecordResponse of(final RoastingRecord roastingRecord) {
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
                .creakInfo(generateCrackInfo(roastingRecord.getCrackPoint(), roastingRecord.getCrackPointTime()))
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

    /**
     * generate CrackInfo object
     * @param crackPointTemps [30.3, 50.3]
     * @param crackPointTimes [2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]
     * @return
     */
    private static CreakInfo generateCrackInfo(final String crackPointTemps, final String crackPointTimes) {
        var temps = convertToFloatList(crackPointTemps);
        var times = convertToMMSSTimeList(crackPointTimes);
        resizeFloatList(temps, 2);
        resizeStringList(times, 2);

        return CreakInfo.builder()
                .firstCrackPointTemp(temps.get(0))
                .firstCrackPointTime(times.get(0))
                .secondCrackPointTemp(temps.get(1))
                .secondCrackPointTime(times.get(1))
                .build();
    }

    static void resizeFloatList(final List<Float> list, int size) {
        while (list.size() < size) {
            list.add(0F);
        }
    }

    static void resizeStringList(final List<String> list, int size) {
        while (list.size() < size) {
            list.add("");
        }
    }

    private static float getSingleTemp(final String temp) {
        var temps = convertToFloatList(temp);
        if (temps.isEmpty()) {
            return 0F;
        }

        return temps.get(0);
    }

    private static String getSingleTime(final String time) {
        var times = convertToMMSSTimeList(time);
        if (times.isEmpty()) {
            return "";
        }

        return times.get(0);
    }

    /**
     * convert string to float list
     * [30.3] -> 30.3
     */
    static List<Float> convertToFloatList(final String input) {
        if (StringUtils.isBlank(input) || "[]".equals(input)) {
            return new ArrayList<>();
        }

        return Arrays.stream(input.substring(1, input.length() - 1).split(","))
                .map(Float::parseFloat)
                .collect(Collectors.toList());
    }

    /**
     * convert time string to MM:SS string list
     * [2024-02-20 15:00:18 +0000] -> 00:18
     */
    static List<String> convertToMMSSTimeList(final String input) {
        if (StringUtils.isBlank(input) || "[]".equals(input)) {
            return new ArrayList<>();
        }

        return Arrays.stream(input.substring(1, input.length() - 1).split(","))
                .map(time -> getHHSSTime(time.trim()))
                .collect(Collectors.toList());
    }

    private static String getHHSSTime(final String item) {
        final var dateTime = ZonedDateTime.parse(item, DATE_TIME_FORMATTER);
        return dateTime.format(TIME_FORMATTER);
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