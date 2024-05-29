package com.site.reon.aggregate.record.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Profile {
    @Column(length = 20000, nullable = false)
    private String fan;

    @Column(length = 20000)
    private String fan2;

    @Column(length = 20000, nullable = false)
    private String heater;

    @Column(length = 20000)
    private String ror;

    @Embedded
    private Temperature temperature;

    @Embedded
    private CrackPoint crackPoint;

    @Embedded
    private TurningPoint turningPoint;

    @Embedded
    private CoolingPoint coolingPoint;

    @Embedded
    private Dispose dispose;
}
