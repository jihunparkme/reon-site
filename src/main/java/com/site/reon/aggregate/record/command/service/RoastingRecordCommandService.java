package com.site.reon.aggregate.record.command.service;

import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.dto.SharePilotRecordRequest;
import com.site.reon.aggregate.record.command.dto.UpdateRecordRequest;

public interface RoastingRecordCommandService {
    void upload(RoastingRecordRequest request);

    void sharePilotRecord(Long recordId, SharePilotRecordRequest request);

    void deleteRecord(Long recordId, Long memberId);

    void updateMemo(Long recordId, UpdateRecordRequest memberId);
}
