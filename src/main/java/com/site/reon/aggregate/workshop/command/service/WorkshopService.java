package com.site.reon.aggregate.workshop.command.service;

import com.site.reon.aggregate.workshop.command.dto.WorkshopSaveRequest;

public interface WorkshopService {
    Long saveWorkshop(WorkshopSaveRequest request, long memberId);

    void delete(Long workshopId);

    void subscribe(Long workshopId, final Long memberId);
}
