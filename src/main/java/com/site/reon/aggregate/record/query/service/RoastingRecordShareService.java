package com.site.reon.aggregate.record.query.service;

import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordResponse;

public interface RoastingRecordShareService {
    ApiRoastingRecordResponse findRoastingRecordByIdAndEmail(Long recordId, String email);
}
