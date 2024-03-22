package com.site.reon.aggregate.common.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SerialNoTest {
    @Test
    void generate_serial_no_test() {
        final ProductNo productNo = ProductNo.of("R2N0");
        final int createdNo = 2;
        final LocalDate date = LocalDate.of(2024, 3, 22);

        final SerialNo serialNo = SerialNo.builder()
                .productNo(productNo)
                .createdNo(createdNo + 1)
                .date(date)
                .build();

        Assertions.assertEquals("R2N0-0003-20240322", serialNo.getNo());
    }

    @Test
    void generate_multiple_serial_no_test() {
        final ProductNo productNo = ProductNo.of("R2N0");
        final int createdNo = 8;
        final LocalDate date = LocalDate.of(2024, 3, 22);

        List<SerialNo> serialNos = IntStream.range(0, 10)
                .mapToObj(i -> SerialNo.builder()
                        .productNo(productNo)
                        .createdNo(createdNo + i + 1)
                        .date(date)
                        .build())
                .collect(Collectors.toList());

        Assertions.assertEquals("R2N0-0009-20240322", serialNos.get(0).getNo());
        Assertions.assertEquals("R2N0-0010-20240322", serialNos.get(1).getNo());
        Assertions.assertEquals("R2N0-0011-20240322", serialNos.get(2).getNo());
        Assertions.assertEquals("R2N0-0012-20240322", serialNos.get(3).getNo());
        Assertions.assertEquals("R2N0-0013-20240322", serialNos.get(4).getNo());
        Assertions.assertEquals("R2N0-0014-20240322", serialNos.get(5).getNo());
        Assertions.assertEquals("R2N0-0015-20240322", serialNos.get(6).getNo());
        Assertions.assertEquals("R2N0-0016-20240322", serialNos.get(7).getNo());
        Assertions.assertEquals("R2N0-0017-20240322", serialNos.get(8).getNo());
        Assertions.assertEquals("R2N0-0018-20240322", serialNos.get(9).getNo());
    }
}