package com.site.reon.aggregate.record.query.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class RoastingRecordResponseTest {

    private RoastingRecordResponse record = new RoastingRecordResponse();

    @Test
    void convertToFloat_single_value() throws Exception {
        String input = "[30.3]";
        List<Float> result = record.convertToFloatList(input);

        List<Float> expect = Arrays.asList(30.3F);
        Assertions.assertEquals(expect, result);
    }

    @Test
    void convertToFloat_multiple_value() throws Exception {
        String input = "[30.3,30.4,30.5]";
        List<Float> result = record.convertToFloatList(input);

        List<Float> expect = Arrays.asList(30.3F, 30.4F, 30.5F);
        Assertions.assertEquals(expect, result);
    }

    @Test
    void convertToFloat_empty_value() throws Exception {
        String input = "[]";
        List<Float> result = record.convertToFloatList(input);

        List<Float> expect = Collections.emptyList();
        Assertions.assertEquals(expect, result);
    }

    @Test
    void convertToFloat_empty_string() throws Exception {
        String input = "";
        List<Float> result = record.convertToFloatList(input);

        List<Float> expect = Collections.emptyList();
        Assertions.assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_single_value() throws Exception {
        String input = "[2024-02-20 15:00:18 +0000]";
        List<String> result = record.convertToMMSSTimeList(input);

        List<String> expect = Arrays.asList("00:18");
        Assertions.assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_multiple_value() throws Exception {
        String input = "[2024-02-20 15:00:18 +0000, 2024-02-20 15:10:19 +0000]";
        List<String> result = record.convertToMMSSTimeList(input);

        List<String> expect = Arrays.asList("00:18", "10:19");
        Assertions.assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_empty_value() throws Exception {
        String input = "[]";
        List<String> result = record.convertToMMSSTimeList(input);

        List<String> expect = Collections.emptyList();
        Assertions.assertEquals(expect, result);
    }

    @Test
    void getHHSSTime_empty_stsring() throws Exception {
        String input = "";
        List<String> result = record.convertToMMSSTimeList(input);

        List<String> expect = Collections.emptyList();
        Assertions.assertEquals(expect, result);
    }

    @Test
    void resizeFloatList_add_new_value() throws Exception {
        List<Float> result = new ArrayList<>();
        result.add(0.1F);

        record.resizeFloatList(result, 2);

        List<Float> expect = Arrays.asList(0.1F, 0F);
        Assertions.assertEquals(expect, result);
    }

    @Test
    void resizeFloatList_same_size() throws Exception {
        List<Float> result = new ArrayList<>();
        result.add(0.1F);
        result.add(0.2F);

        record.resizeFloatList(result, 2);

        List<Float> expect = Arrays.asList(0.1F, 0.2F);
        Assertions.assertEquals(expect, result);
    }

    @Test
    void resizeFloatList_empty_list() throws Exception {
        List<Float> result = new ArrayList<>();

        record.resizeFloatList(result, 2);

        List<Float> expect = Arrays.asList(0F, 0F);
        Assertions.assertEquals(expect, result);
    }

    @Test
    void resizeStringList_add_new_value() throws Exception {
        List<String> result = new ArrayList<>();
        result.add("00:00");

        record.resizeStringList(result, 2);

        List<String> expect = Arrays.asList("00:00", "");
        Assertions.assertEquals(expect, result);
    }

    @Test
    void resizeStringList_same_size() throws Exception {
        List<String> result = new ArrayList<>();
        result.add("00:03");
        result.add("00:04");

        record.resizeStringList(result, 2);

        List<String> expect = Arrays.asList("00:03", "00:04");
        Assertions.assertEquals(expect, result);
    }

    @Test
    void resizeStringList_empty_list() throws Exception {
        List<String> result = new ArrayList<>();

        record.resizeStringList(result, 2);

        List<String> expect = Arrays.asList("", "");
        Assertions.assertEquals(expect, result);
    }

    @Test
    void calculateRoastingLogsInSeconds() {
        String tempLog = "[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]";

        final int result = record.calculateRoastingLogsInSeconds(tempLog);

        Assertions.assertEquals(81, result);
    }

    @Test
    void calculateDevelopmentTimeRatio() {
        final int totalRoastingSecondsTime = 10 * 60;
        final String firstCrackPointTime = "07:00";

        final float result = record.calculateDevelopmentTimeRatio(totalRoastingSecondsTime, firstCrackPointTime);

        Assertions.assertEquals(30.0, result);
    }
}