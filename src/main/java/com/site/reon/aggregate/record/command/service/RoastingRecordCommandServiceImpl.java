package com.site.reon.aggregate.record.command.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.dto.SharePilotRecordRequest;
import com.site.reon.global.security.exception.NotFoundRoastingRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoastingRecordCommandServiceImpl implements RoastingRecordCommandService {

    private final RoastingRecordRepository recordRepository;

    @Override
    @Transactional
    public void upload(final RoastingRecordRequest request) {
        final var roastingRecord = request.toEntity(request.getMemberId());
        recordRepository.save(roastingRecord);
    }

    @Override
    public void sharePilotRecord(final Long id, final SharePilotRecordRequest request) {
        final Optional<RoastingRecord> recordOpt = recordRepository.findById(id);
        if (recordOpt.isEmpty()) {
            new NotFoundRoastingRecordException();
        }

        final RoastingRecord roastingRecord = recordOpt.get();
        roastingRecord.sharePilot(request.getPilot());
        recordRepository.save(roastingRecord);
    }
}
