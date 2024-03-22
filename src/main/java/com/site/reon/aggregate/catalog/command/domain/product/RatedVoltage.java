package com.site.reon.aggregate.catalog.command.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RatedVoltage {
    V110("110V"),
    V220("220V"),
    ;

    private final String value;
}
