package com.site.reon.aggregate.record.service;

import com.site.reon.aggregate.record.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.entity.RoastingRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoastingRecordServiceImpl implements RoastingRecordService {

    private final RoastingRecordRepository recordRepository;

    @Override
    public void upload(RoastingRecordRequest request) {
        // TODO: S/N 로 memberId 검색
        long memberId = 2L;
        RoastingRecord roastingRecord = request.toEntity(memberId);

        recordRepository.save(roastingRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoastingRecord> findAllSortByIdDescPaging(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return recordRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public RoastingRecordResponse findRoastingRecordBy(Long id) {
        Optional<RoastingRecord> roastingRecordOpt = recordRepository.findById(id);
        if (roastingRecordOpt.isEmpty()) {
            return RoastingRecordResponse.EMPTY;
        }

        return RoastingRecordResponse.of(roastingRecordOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public RoastingRecordResponse findRoastingRecordBy(String roasterSn) {
        Optional<RoastingRecord> roastingRecordOpt = recordRepository.findByRoasterSn(roasterSn);
        if (roastingRecordOpt.isEmpty()) {
            return RoastingRecordResponse.EMPTY;
        }

        return RoastingRecordResponse.of(roastingRecordOpt.get());
    }
}