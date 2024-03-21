package com.site.reon.aggregate.record.query.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class RoastingRecordListResponseTest {

    @Test
    void refine_LocalDateTime() {
        final LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final String date = now.toLocalDate().toString();
        final String time = now.toLocalTime().withNano(0).toString();

        System.out.println(date);
        System.out.println(time);
        Assertions.assertEquals(now.getYear()
                + "-" + getFormattedNumber(now.getMonthValue())
                + "-" + getFormattedNumber(now.getDayOfMonth()), date);
        Assertions.assertEquals(getFormattedNumber(now.getHour())
                + ":" + getFormattedNumber(now.getMinute())
                + ":" + getFormattedNumber(now.getSecond()), time);
    }

    private String getFormattedNumber(final int now) {
        return String.format("%02d", now);
    }
}

