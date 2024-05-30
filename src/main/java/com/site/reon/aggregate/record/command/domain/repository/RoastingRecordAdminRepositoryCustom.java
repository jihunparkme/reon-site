package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import org.springframework.data.domain.Page;

public interface RoastingRecordAdminRepositoryCustom {
    Page<RoastingRecord> findAllByAdminFilter(AdminRecordSearchRequestParam pageable);
}
