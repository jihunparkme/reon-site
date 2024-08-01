package com.site.reon.aggregate.record.query.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RoastingRecordResponseTest {
    @Test
    void calculateDtr_Area_contain_only_first_crack() throws Exception {
        final RoastingRecordResponse record = RoastingRecordResponse.builder()
                .turningPointTime("00:49")
                .creakInfo(RoastingRecordResponse.CreakInfo.builder()
                        .firstCrackPointTime("06:35")
                        .secondCrackPointTime("")
                        .build())
                .coolingPointTime("07:19")
                .build();

        final RoastingRecordResponse result = record.calculateDtrArea();

        Assertions.assertThat(result.getDtrTimeSeconds()).isEqualTo(390);
        Assertions.assertThat(result.getDtrFirstPercent()).isEqualTo(88.7f);
        Assertions.assertThat(result.getDtrSecondPercent()).isEqualTo(0.0f);
        Assertions.assertThat(result.getDtrThirdPercent()).isEqualTo(11.3f);
    }

    @Test
    void calculateDtr_Area_contain_contain_second_crack() throws Exception {
        final RoastingRecordResponse record = RoastingRecordResponse.builder()
                .turningPointTime("01:08")
                .creakInfo(RoastingRecordResponse.CreakInfo.builder()
                        .firstCrackPointTime("07:01")
                        .secondCrackPointTime("08:15")
                        .build())
                .coolingPointTime("08:43")
                .build();

        final RoastingRecordResponse result = record.calculateDtrArea();

        Assertions.assertThat(result.getDtrTimeSeconds()).isEqualTo(455);
        Assertions.assertThat(result.getDtrFirstPercent()).isEqualTo(77.6f);
        Assertions.assertThat(result.getDtrSecondPercent()).isEqualTo(16.3f);
        Assertions.assertThat(result.getDtrThirdPercent()).isEqualTo(6.2f);
    }
}