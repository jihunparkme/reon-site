package com.site.reon.aggregate.record.command.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.dto.SharePilotRecordRequest;
import com.site.reon.aggregate.record.command.dto.UpdateRecordRequest;
import com.site.reon.global.security.exception.DataAccessPermissionException;
import com.site.reon.global.security.exception.NotFoundRoastingRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoastingRecordCommandServiceImpl implements RoastingRecordCommandService {

    private final RoastingRecordRepository recordRepository;

    @Override
    public void upload(final RoastingRecordRequest request) {
        final var roastingRecord = request.toEntity(request.getMemberId());
        recordRepository.save(roastingRecord);
    }

    @Override
    public void sharePilotRecord(final Long recordId, final SharePilotRecordRequest request) {
        final Optional<RoastingRecord> recordOpt = recordRepository.findById(recordId);
        if (recordOpt.isEmpty()) {
            throw new NotFoundRoastingRecordException();
        }

        final RoastingRecord roastingRecord = recordOpt.get();
        roastingRecord.sharePilot(request.getPilot());
        recordRepository.save(roastingRecord);
    }

    @Override
    public void deleteRecord(final Long recordId, final Long memberId) {
        final Optional<RoastingRecord> recordOpt = getRoastingRecord(recordId, memberId);

        final RoastingRecord roastingRecord = recordOpt.get();
        recordRepository.delete(roastingRecord);
    }

    @Override
    public void updateMemo(final Long recordId, final UpdateRecordRequest request) {
        final Optional<RoastingRecord> recordOpt = getRoastingRecord(recordId, request.getMemberId());

        final RoastingRecord roastingRecord = recordOpt.get();
        roastingRecord.updateMemo(request.getMemo());
        recordRepository.save(roastingRecord);
    }

    @Override
    public void saveSubscribe(final RoastingRecord subscribed) {
        recordRepository.save(subscribed);
    }

    private Optional<RoastingRecord> getRoastingRecord(final Long recordId, final Long memberId) {
        final Optional<RoastingRecord> recordOpt = recordRepository.findByIdAndRoastingInfoMemberId(recordId, memberId);
        if (recordOpt.isEmpty()) {
            throw new DataAccessPermissionException("You do not have permission to access this data.");
        }
        return recordOpt;
    }
}
