package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoastingRecordRepository extends JpaRepository<RoastingRecord, Long> {
    Page<RoastingRecord> findByMemberId(Long memberId, Pageable pageable);

    Optional<RoastingRecord> findByRoasterSn(@Param(value = "roasterSn") String roasterSn);

    Optional<RoastingRecord> findByIdAndMemberId(@Param(value = "id") long id,
                                                 @Param(value = "memberId") long memberId);

    Optional<List<RoastingRecord>> findByMemberId(@Param(value = "memberId") long memberId);

    Optional<List<RoastingRecord>> findByPilotTrue();
}