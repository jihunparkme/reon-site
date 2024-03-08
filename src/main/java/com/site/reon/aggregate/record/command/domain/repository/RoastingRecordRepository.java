package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoastingRecordRepository extends JpaRepository<RoastingRecord, Long> {
    @Query("SELECT rr " +
            "FROM RoastingRecord rr " +
            "WHERE rr.roasterSn=:roasterSn")
    Optional<RoastingRecord> findByRoasterSn(@Param(value = "roasterSn") String roasterSn);

    @Query("SELECT rr " +
            "FROM RoastingRecord rr " +
            "WHERE rr.memberId =:memberId")
    Optional<List<RoastingRecord>> findRoastingRecordListBy(@Param(value = "memberId") long memberId);
}
