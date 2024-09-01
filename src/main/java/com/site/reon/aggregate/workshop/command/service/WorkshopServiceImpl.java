package com.site.reon.aggregate.workshop.command.service;

import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import com.site.reon.aggregate.workshop.command.domain.Workshop;
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
    public Long saveWorkshop(final WorkshopSaveRequest request, final long memberId) {
        roastingRecordFindService.findRoastingRecordBy(request.getRecordId(), memberId);
        final Workshop workshop = request.toWorkshop();
        return workshopRepository.save(workshop).getId();
    }
}
