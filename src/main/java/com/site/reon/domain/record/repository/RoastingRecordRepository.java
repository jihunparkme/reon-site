package com.site.reon.domain.record.repository;

import com.site.reon.domain.record.entity.RoastingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoastingRecordRepository extends JpaRepository<RoastingRecord, Long> {
}
