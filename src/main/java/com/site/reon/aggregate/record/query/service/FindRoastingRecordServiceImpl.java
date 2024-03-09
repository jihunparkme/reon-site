package com.site.reon.aggregate.record.query.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
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
public class FindRoastingRecordServiceImpl implements FindRoastingRecordService {

    private final RoastingRecordRepository recordRepository;

    @Override
    public Page<RoastingRecord> findAllSortByIdDescPaging(final int page, final int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return recordRepository.findAll(pageable);
    }

    @Override
    public RoastingRecordResponse findRoastingRecordBy(final Long id) {
        final var roastingRecordOpt = recordRepository.findById(id);
        if (roastingRecordOpt.isEmpty()) {
            return RoastingRecordResponse.EMPTY;
        }

        return RoastingRecordResponse.of(roastingRecordOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public RoastingRecordResponse findRoastingRecordBy(final String roasterSn) {
        final var roastingRecordOpt = recordRepository.findByRoasterSn(roasterSn);
        if (roastingRecordOpt.isEmpty()) {
            return RoastingRecordResponse.EMPTY;
        }

        return RoastingRecordResponse.of(roastingRecordOpt.get());
    }

    @Override
    public List<RoastingRecordListResponse> findRoastingRecordListBy(final long memberId) {
        Optional<List<RoastingRecord>> roastingRecordsOpt = recordRepository.findRoastingRecordListBy(memberId);
        if (roastingRecordsOpt.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        final List<RoastingRecord> roastingRecords = roastingRecordsOpt.get();
        return roastingRecords.stream()
                .map(RoastingRecordListResponse::of)
                .collect(Collectors.toList());
    }
}
