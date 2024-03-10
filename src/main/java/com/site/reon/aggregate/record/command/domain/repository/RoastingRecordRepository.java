package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoastingRecordRepository extends JpaRepository<RoastingRecord, Long> {
    Optional<RoastingRecord> findByRoasterSn(@Param(value = "roasterSn") String roasterSn);

    Optional<List<RoastingRecord>> findByMemberId(@Param(value = "memberId") long memberId);
}