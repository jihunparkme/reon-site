package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.dto.RoastingRecordsResponse;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import org.springframework.data.domain.Page;

public interface RoastingRecordAdminRepositoryCustom {
    Page<RoastingRecordsResponse> findAllByAdminFilter(AdminRecordSearchRequestParam pageable);
}
