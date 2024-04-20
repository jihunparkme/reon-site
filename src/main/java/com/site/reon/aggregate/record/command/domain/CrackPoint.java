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
public class CrackPoint {
    @Column(length = 100)
    private String crackPoint;

    @Column(length = 100)
    private String crackPointTime;
}
