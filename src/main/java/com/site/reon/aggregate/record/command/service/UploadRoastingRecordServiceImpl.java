package com.site.reon.aggregate.record.command.service;

import com.site.reon.aggregate.record.command.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UploadRoastingRecordServiceImpl implements UploadRoastingRecordService {

    private final RoastingRecordRepository recordRepository;

    @Override
    @Transactional
    public void upload(final RoastingRecordRequest request) {
        // TODO: S/N 로 memberId 검색
        long memberId = request.getMemberId();
        if (memberId == 0) {
            memberId = 2L;
        }
        final var roastingRecord = request.toEntity(memberId);

        recordRepository.save(roastingRecord);
    }
}
