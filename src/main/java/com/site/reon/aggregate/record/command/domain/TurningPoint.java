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
public class TurningPoint {
    @Column(length = 100)
    private String turningPointTemp;

    @Column(length = 100)
    private String turningPointTime;
}
