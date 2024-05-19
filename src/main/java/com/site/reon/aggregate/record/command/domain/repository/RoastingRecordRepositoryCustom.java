package com.site.reon.aggregate.record.command.domain.repository;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.SearchRequestParam;
import org.springframework.data.domain.Page;

public interface RoastingRecordRepositoryCustom {
    Page<RoastingRecord> findByFilter(Long memberId, SearchRequestParam pageable);
}
