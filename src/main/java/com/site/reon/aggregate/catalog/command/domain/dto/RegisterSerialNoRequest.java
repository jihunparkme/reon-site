package com.site.reon.aggregate.catalog.command.domain.dto;

import java.util.List;

public record RegisterSerialNoRequest(
        List<String> serialNos
) {
}
