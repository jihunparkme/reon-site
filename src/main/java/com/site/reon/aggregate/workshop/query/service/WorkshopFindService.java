package com.site.reon.aggregate.workshop.query.service;

import com.site.reon.aggregate.workshop.query.dto.WorkshopResponse;

public interface WorkshopFindService {
    WorkshopResponse findWorkshop(Long id);
}
