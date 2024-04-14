package com.site.reon.aggregate.member.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductInfo {
    @Column(length = 100)
    private String prdCode;

    @Column(length = 100)
    private String roasterSn;
}
