package com.site.reon.aggregate.workshop.command.service;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import com.site.reon.aggregate.workshop.command.domain.WorkshopRepository;
import com.site.reon.aggregate.workshop.command.dto.WorkshopSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final RoastingRecordFindService roastingRecordFindService;

    @Override
    public String saveWorkshop(final WorkshopSaveRequest request, final long memberId) {
        final RoastingRecord record = roastingRecordFindService.findRoastingRecordBy(request.getRecordId(), memberId);
        return null;
    }
}
