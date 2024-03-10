package com.site.reon.aggregate.record.command.service;

import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;

public interface UploadRoastingRecordService {
    void upload(RoastingRecordRequest request);
}
