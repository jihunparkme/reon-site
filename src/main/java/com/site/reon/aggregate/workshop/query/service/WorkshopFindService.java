package com.site.reon.aggregate.workshop.query.service;

import com.site.reon.aggregate.workshop.query.dto.WorkshopResponse;
import com.site.reon.aggregate.workshop.query.dto.WorkshopSearchRequestParam;
import com.site.reon.aggregate.workshop.query.dto.WorkshopsResponse;
import org.springframework.data.domain.Page;

public interface WorkshopFindService {
    WorkshopResponse findWorkshop(Long id);

    boolean isSubscribed(Long recordId, final Long memberId);

    Page<WorkshopsResponse> findAllByFilter(WorkshopSearchRequestParam param);
}
