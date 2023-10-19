package com.site.reon.domain.record.service;

import com.site.reon.domain.record.dto.RoastingRecordRequest;
import com.site.reon.domain.record.entity.RoastingRecord;
import com.site.reon.domain.record.repository.RoastingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoastingRecordServiceImpl implements RoastingRecordService {

    private final RoastingRecordRepository recordRepository;

    @Override
    public void upload(RoastingRecordRequest request) {
        // TODO: S/N 로 userId 검색
        long userId = 2L;
        RoastingRecord roastingRecord = request.toEntity(userId);

        recordRepository.save(roastingRecord);
    }
}
