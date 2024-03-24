package com.site.reon.aggregate.record.command.service;

import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;

public interface RoastingRecordCommandService {
    void upload(RoastingRecordRequest request);
}
