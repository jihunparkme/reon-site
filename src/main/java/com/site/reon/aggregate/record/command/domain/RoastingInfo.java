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
public class RoastingInfo {
    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String roasterSn;

    @Column(nullable = false)
    private long memberId;

    @Column(length = 20000)
    private String memo;

    public void updateMemo(final String memo) {
        this.memo = memo;
    }
}
