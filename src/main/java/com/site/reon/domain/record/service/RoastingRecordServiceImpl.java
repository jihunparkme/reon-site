package com.site.reon.domain.record.service;

import com.site.reon.domain.record.dto.RoastingRecordRequest;
import com.site.reon.domain.record.entity.RoastingRecord;
import com.site.reon.domain.record.repository.RoastingRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = true)
    public Page<RoastingRecord> findAllSortByIdDescPaging(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return recordRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public RoastingRecord findRoastingRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 기록이 존재하지 않습니다. id=" + id));
    }
}
