package com.site.reon.aggregate.record.util;

import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecordUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("mm:ss");

    /**
     * generate CrackInfo object
     * @param crackPointTemps [30.3, 50.3]
     * @param crackPointTimes [2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]
     * @return
     */
    public static RoastingRecordResponse.CreakInfo generateCrackInfo(final String crackPointTemps, final String crackPointTimes) {
        var temps = convertToFloatList(crackPointTemps);
        var times = convertToMMSSTimeList(crackPointTimes);
        resizeFloatList(temps, 2);
        resizeStringList(times, 2);

        return RoastingRecordResponse.CreakInfo.builder()
                .firstCrackPointTemp(temps.get(0))
                .firstCrackPointTime(times.get(0))
                .secondCrackPointTemp(temps.get(1))
                .secondCrackPointTime(times.get(1))
                .build();
    }

    public static void resizeFloatList(final List<Float> list, int size) {
        while (list.size() < size) {
            list.add(0F);
        }
    }

    public static void resizeStringList(final List<String> list, int size) {
        while (list.size() < size) {
            list.add("");
        }
    }

    public static float getSingleTemp(final String temp) {
        var temps = convertToFloatList(temp);
        if (temps.isEmpty()) {
            return 0F;
        }

        return temps.get(0);
    }

    public static String getSingleTime(final String time) {
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
    public static List<Float> convertToFloatList(final String input) {
        if (StringUtils.isEmpty(input) || "[]".equals(input)) {
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
    public static List<String> convertToMMSSTimeList(final String input) {
        if (StringUtils.isEmpty(input) || "[]".equals(input)) {
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

    public static int calculateRoastingLogsInSeconds(final String tempLog) {
        return convertToFloatList(tempLog).size();
    }

    public static float calculateDevelopmentTimeRatio(final int totalRoastingSecondsTime, final String firstCrackPointTime) {
        try {
            final float firstCrackPointSecondsTime = getHHSSTimeToSeconds(firstCrackPointTime);
            // 발현 시간: 첫 번째 크랙이 시작된 후부터 로스팅이 완료될 때까지의 시간
            final float developmentSecondsTime = totalRoastingSecondsTime - firstCrackPointSecondsTime;
            final float dtr = (developmentSecondsTime / totalRoastingSecondsTime) * 100;
            return (float) (Math.round(dtr * 100.0) / 100.0);
        } catch (Exception e) {
            return 0.0F;
        }
    }

    private static float getHHSSTimeToSeconds(final String firstCrackPointTime) {
        try {
            final String[] parts = firstCrackPointTime.split(":");
            final int minutes = Integer.parseInt(parts[0]);
            final int seconds = Integer.parseInt(parts[1]);
            return (float) ((minutes * 60) + seconds);
        } catch (Exception e) {
            return 0.0F;
        }
    }
}
