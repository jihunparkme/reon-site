package com.site.reon.aggregate.record.query.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.dto.RecordSearchRequestParam;
import com.site.reon.aggregate.record.query.dto.api.RoastingRecordsAndPilotsResponse;
import com.site.reon.global.security.exception.NotFoundRoastingRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoastingRecordFindServiceImpl implements RoastingRecordFindService {

    private final RoastingRecordRepository recordRepository;

    @Override
    public Page<RoastingRecord> findAllByMemberIdOrderByIdDescPaging(final long memberId, final int page, final int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return recordRepository.findByRoastingInfoMemberId(memberId, pageable);
    }

    @Override
    public Page<RoastingRecord> findAllOrderByIdDescPaging(final int page, final int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return recordRepository.findAll(pageable);
    }

    @Override
    public Page<RoastingRecord> findAllByFilter(final long memberId, final RecordSearchRequestParam param) {
        return recordRepository.findAllByFilter(memberId, param);
    }

    @Override
    public Page<RoastingRecord> findAllByAdminFilter(final AdminRecordSearchRequestParam param) {
        return recordRepository.findAllByAdminFilter(param);
    }

    @Override
    public RoastingRecordResponse findRoastingRecordBy(final Long id) {
        return RoastingRecordResponse.of(findRoastingRecord(id));
    }

    @Override
    public RoastingRecord findRoastingRecordBy(final long recordId, final long memberId) {
        final Optional<RoastingRecord> roastingRecordOpt = recordRepository.findByIdAndRoastingInfoMemberId(recordId, memberId);
        if (roastingRecordOpt.isEmpty()) {
            throw new IllegalArgumentException("You do not have permission to access this data.");
        }

        return roastingRecordOpt.get();
    }

    @Override
    public RoastingRecord findRoastingRecordBy(final long recordId, final long memberId, final boolean pilot) {
        if (pilot) {
            return findRoastingRecord(recordId);
        }
        return this.findRoastingRecordBy(recordId, memberId);
    }

    @Override
    public List<RoastingRecordListResponse> findRoastingRecordListBy(final long memberId) {
        final List<RoastingRecord> roastingRecords = findMemberRecords(memberId);

        return roastingRecords.stream()
                .map(RoastingRecordListResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public RoastingRecordsAndPilotsResponse findRoastingRecordsAndPilotsBy(final Long memberId) {
        final List<RoastingRecord> personalRecords = findMemberRecords(memberId);
        final List<RoastingRecord> pilotRecords = findPilotRecords();

        return RoastingRecordsAndPilotsResponse.builder()
                .personalRecords(personalRecords.stream()
                        .map(RoastingRecordListResponse::of)
                        .collect(Collectors.toList()))
                .pilotRecords(pilotRecords.stream()
                        .map(RoastingRecordListResponse::of)
                        .collect(Collectors.toList()))
                .build();
    }

    private RoastingRecord findRoastingRecord(final Long id) {
        final var roastingRecordOpt = recordRepository.findById(id);
        if (roastingRecordOpt.isEmpty()) {
            throw new NotFoundRoastingRecordException();
        }

        return roastingRecordOpt.get();
    }

    private List<RoastingRecord> findMemberRecords(final long memberId) {
        Optional<List<RoastingRecord>> roastingRecordsOpt = recordRepository.findByRoastingInfoMemberId(memberId);
        if (roastingRecordsOpt.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return roastingRecordsOpt.get();
    }

    private List<RoastingRecord> findPilotRecords() {
        final Optional<List<RoastingRecord>> pilotRecordOpt = recordRepository.findByPilotTrue();
        if (pilotRecordOpt.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return pilotRecordOpt.get();
    }
}
