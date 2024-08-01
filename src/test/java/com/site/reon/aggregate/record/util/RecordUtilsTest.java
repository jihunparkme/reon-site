package com.site.reon.aggregate.record.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordUtilsTest {

    @Test
    void refine_LocalDateTime() {
        final LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final String date = now.toLocalDate().toString();
        final String time = now.toLocalTime().withNano(0).toString();

        System.out.println(date);
        System.out.println(time);
        assertEquals(now.getYear()
                + "-" + getFormattedNumber(now.getMonthValue())
                + "-" + getFormattedNumber(now.getDayOfMonth()), date);
        assertEquals(getFormattedNumber(now.getHour())
                + ":" + getFormattedNumber(now.getMinute())
                + ":" + getFormattedNumber(now.getSecond()), time);
    }

    private String getFormattedNumber(final int now) {
        return String.format("%02d", now);
    }

    @Test
    void convertToFloat_single_value() throws Exception {
        String input = "[30.3]";
        List<Float> result = RecordUtils.convertToFloatList(input);

        List<Float> expect = Arrays.asList(30.3F);
        assertEquals(expect, result);
    }

    @Test
    void convertToFloat_multiple_value() throws Exception {
        String input = "[30.3,30.4,30.5]";
        List<Float> result = RecordUtils.convertToFloatList(input);

        List<Float> expect = Arrays.asList(30.3F, 30.4F, 30.5F);
        assertEquals(expect, result);
    }

    @Test
    void convertToFloat_empty_value() throws Exception {
        String input = "[]";
        List<Float> result = RecordUtils.convertToFloatList(input);

        List<Float> expect = Collections.emptyList();
        assertEquals(expect, result);
    }

    @Test
    void convertToFloat_empty_string() throws Exception {
        String input = "";
        List<Float> result = RecordUtils.convertToFloatList(input);

        List<Float> expect = Collections.emptyList();
        assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_single_value() throws Exception {
        String input = "[2024-02-20 15:00:18 +0000]";
        List<String> result = RecordUtils.convertToMMSSTimeList(input);

        List<String> expect = Arrays.asList("00:18");
        assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_multiple_value() throws Exception {
        String input = "[2024-02-20 15:00:18 +0000, 2024-02-20 15:10:19 +0000]";
        List<String> result = RecordUtils.convertToMMSSTimeList(input);

        List<String> expect = Arrays.asList("00:18", "10:19");
        assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_empty_value() throws Exception {
        String input = "[]";
        List<String> result = RecordUtils.convertToMMSSTimeList(input);

        List<String> expect = Collections.emptyList();
        assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_empty_stsring() throws Exception {
        String input = "";
        List<String> result = RecordUtils.convertToMMSSTimeList(input);

        List<String> expect = Collections.emptyList();
        assertEquals(expect, result);
    }

    @Test
    void resizeFloatList_add_new_value() throws Exception {
        List<Float> result = new ArrayList<>();
        result.add(0.1F);

        RecordUtils.resizeFloatList(result, 2);

        List<Float> expect = Arrays.asList(0.1F, 0F);
        assertEquals(expect, result);
    }

    @Test
    void resizeFloatList_same_size() throws Exception {
        List<Float> result = new ArrayList<>();
        result.add(0.1F);
        result.add(0.2F);

        RecordUtils.resizeFloatList(result, 2);

        List<Float> expect = Arrays.asList(0.1F, 0.2F);
        assertEquals(expect, result);
    }

    @Test
    void resizeFloatList_empty_list() throws Exception {
        List<Float> result = new ArrayList<>();

        RecordUtils.resizeFloatList(result, 2);

        List<Float> expect = Arrays.asList(0F, 0F);
        assertEquals(expect, result);
    }

    @Test
    void resizeStringList_add_new_value() throws Exception {
        List<String> result = new ArrayList<>();
        result.add("00:00");

        RecordUtils.resizeStringList(result, 2);

        List<String> expect = Arrays.asList("00:00", "");
        assertEquals(expect, result);
    }

    @Test
    void resizeStringList_same_size() throws Exception {
        List<String> result = new ArrayList<>();
        result.add("00:03");
        result.add("00:04");

        RecordUtils.resizeStringList(result, 2);

        List<String> expect = Arrays.asList("00:03", "00:04");
        assertEquals(expect, result);
    }

    @Test
    void resizeStringList_empty_list() throws Exception {
        List<String> result = new ArrayList<>();

        RecordUtils.resizeStringList(result, 2);

        List<String> expect = Arrays.asList("", "");
        assertEquals(expect, result);
    }

    @Test
    void calculateRoastingLogsInSeconds() {
        String tempLog = "[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]";

        final int result = RecordUtils.calculateRoastingLogsInSeconds(tempLog);

        assertEquals(81, result);
    }

    @Test
    void time_to_seconds_test() {
        final String pointTimeStr = "[2024-02-20 15:01:10 +0000]";
        final List<String> pointTimes = RecordUtils.convertToMMSSTimeList(pointTimeStr);

        final String pointTime = pointTimes.get(0);
        assertEquals("01:10", pointTime);

        final int result = RecordUtils.getHHSSTimeToSeconds(pointTime);
        assertEquals(70, result);
    }
    
    @Test 
    void when_getPointTimeToSeconds_then_return_converted_time() {
        final String pointTimeStr = "[2024-02-20 15:01:10 +0000]";

        final float result = RecordUtils.getPointTimeToSeconds(pointTimeStr, 90);

        assertEquals(70, result);
    }

    @Test
    void when_getPointTimeToSeconds_then_return_default_time() {
        final String pointTimeStr = "[]";

        final float result = RecordUtils.getPointTimeToSeconds(pointTimeStr, 90);

        assertEquals(90, result);
    }

    @Test
    void when_calculateDevelopmentTimeRatio_then_return_dtr() {
        final int totalRoastingSecondsTime = 10 * 60;
        final String firstCrackPointTime = "07:00";

        final float result = RecordUtils.calculateDevelopmentTimeRatio(totalRoastingSecondsTime, firstCrackPointTime);

        assertEquals(30.0, result);
    }
}