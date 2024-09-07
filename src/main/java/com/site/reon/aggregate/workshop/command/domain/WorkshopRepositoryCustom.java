package com.site.reon.aggregate.workshop.command.domain;

import com.site.reon.aggregate.workshop.query.dto.WorkshopSearchRequestParam;
import com.site.reon.aggregate.workshop.query.dto.WorkshopsResponse;
import org.springframework.data.domain.Page;

public interface WorkshopRepositoryCustom {
    Page<WorkshopsResponse> findAllByFilter(WorkshopSearchRequestParam param);
}
