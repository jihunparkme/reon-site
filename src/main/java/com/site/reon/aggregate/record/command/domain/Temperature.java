package com.site.reon.aggregate.record.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Temperature {
    @Column(length = 20000, nullable = false)
    private String temp1;

    @Column(length = 20000, nullable = false)
    private String temp2;

    @Column(length = 20000, nullable = false)
    private String temp3;

    @Column(length = 20000, nullable = false)
    private String temp4;
}
