package com.site.reon.global;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CleanupTable {
    MEMBER("member_id", 3),
    MEMBER_AUTHORITY("member_id", 4),
    ;

    private final String primaryKey;
    private final int index;

    public String primaryKey() {
        return primaryKey;
    }

    public int index() {
        return index;
    }
}
