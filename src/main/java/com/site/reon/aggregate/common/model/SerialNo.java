package com.site.reon.aggregate.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SerialNo {
    @Column(name= "serial_no", length = 100, nullable = false)
    private String no;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean activated;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Builder
    public SerialNo(final ProductNo productNo, final int createdNo, final LocalDate date) {
        this.no = productNo.getNo() + "-" + String.format("%04d", createdNo) + "-" + date.format(formatter);
        this.activated = false;
    }
}
