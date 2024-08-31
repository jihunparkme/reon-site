package com.site.reon.aggregate.workshop.command.service;

import com.site.reon.aggregate.workshop.command.dto.WorkshopSaveRequest;

public interface WorkshopService {
    String saveWorkshop(WorkshopSaveRequest request, long memberId);
}
