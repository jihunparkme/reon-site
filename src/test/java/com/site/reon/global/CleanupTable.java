package com.site.reon.global;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CleanupTable {
    MEMBER("member_id", 1),
    MEMBER_AUTHORITY("member_id", 1),
    PRODUCT("product_id", 1),
    CATEGORY("category_id", 1);

    private final String primaryKey;
    private final int index;

    public String primaryKey() {
        return primaryKey;
    }

    public int index() {
        return index;
    }
}
