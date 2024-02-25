package com.site.reon.aggregate.record.service.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}