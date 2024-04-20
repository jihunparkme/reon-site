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
public class Profile {
    @Column(length = 20000, nullable = false)
    private String fan;

    @Column(length = 20000, nullable = false)
    private String heater;

    @Column(length = 20000)
    private String ror;
}
