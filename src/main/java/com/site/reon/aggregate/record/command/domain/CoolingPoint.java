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
public class CoolingPoint {
    @Column(length = 100)
    private String coolingPointTemp;

    @Column(length = 100)
    private String coolingPointTime;
}
