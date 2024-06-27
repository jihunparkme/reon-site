package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoastingRecordRepository extends JpaRepository<RoastingRecord, Long>, RoastingRecordRepositoryCustom, RoastingRecordAdminRepositoryCustom {
    Page<RoastingRecord> findByRoastingInfoMemberId(Long memberId, Pageable pageable);

    Optional<RoastingRecord> findByRoastingInfoRoasterSn(@Param(value = "roasterSn") String roasterSn);

    Optional<RoastingRecord> findByIdAndRoastingInfoMemberId(@Param(value = "id") long id,
                                                             @Param(value = "memberId") long memberId);

    Optional<List<RoastingRecord>> findByIdInAndRoastingInfoMemberId(@Param(value = "id") List<Long> id,
                                                             @Param(value = "memberId") long memberId);

    Optional<List<RoastingRecord>> findByRoastingInfoMemberId(@Param(value = "memberId") long memberId);

    Optional<List<RoastingRecord>> findByPilotTrue();
}