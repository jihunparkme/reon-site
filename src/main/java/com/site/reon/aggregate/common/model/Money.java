package com.site.reon.aggregate.common.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Money {
    private int value;

    public Money multiply(int multiplier) {
        return new Money(value * multiplier);
    }
}
