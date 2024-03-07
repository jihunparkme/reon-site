package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.domain.RoastingRecord;
import com.site.reon.aggregate.record.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.service.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.service.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.service.dto.RoastingRecordListResponse;
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
@RequiredArgsConstructor
public class RoastingRecordServiceImpl implements RoastingRecordService {

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

    @Override
    @Transactional(readOnly = true)
    public Page<RoastingRecord> findAllSortByIdDescPaging(final int page, final int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return recordRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
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
    public List<RoastingRecordListResponse> findRoastingRecordListBy(final String email, final String authClientName) {
        final OAuth2Client oAuth2Client = OAuth2Client.of(authClientName);
        if (oAuth2Client.isNotEmpty()) {
            OAuth2Client.validateClientName(authClientName.toLowerCase());
        }

        Optional<List<RoastingRecord>> roastingRecordsOpt = recordRepository.findRoastingRecordListBy(email, oAuth2Client);
        if (roastingRecordsOpt.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        final List<RoastingRecord> roastingRecords = roastingRecordsOpt.get();
        return roastingRecords.stream()
                .map(RoastingRecordListResponse::of)
                .collect(Collectors.toList());
    }
}
